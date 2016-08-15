package org.ehuacui.controller;

import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.interceptor.ApiInterceptor;
import org.ehuacui.module.Collect;
import org.ehuacui.module.Notification;
import org.ehuacui.module.Reply;
import org.ehuacui.module.Topic;
import org.ehuacui.module.TopicAppend;
import org.ehuacui.module.User;
import org.ehuacui.utils.SolrUtil;
import org.ehuacui.utils.StrUtil;
import org.ehuacui.ext.route.ControllerBind;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/api")
public class ApiController extends BaseController {

    /**
     * 获取显示的版块列表
     */
    public void sections() {
        success(ServiceHolder.sectionService.findByShowStatus(true));
    }

    /**
     * 话题列表
     */
    public void topics() {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            tab = "all";
        }
        Page<Topic> page = ServiceHolder.topicService.page(getParaToInt("p", 1), PropKit.getInt("pageSize", 20), tab);
        //处理数据
        for(Topic topic: page.getList()) {
            topic.remove("content", "is_delete", "show_status");
        }
        success(page);
    }

    /**
     * 话题详情
     * @throws UnsupportedEncodingException
     */
    public void topic() throws UnsupportedEncodingException {
        Integer tid = getParaToInt(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        Topic topic = ServiceHolder.topicService.findById(tid);
        if (topic == null) {
            error("话题不存在");
        } else {
            //查询追加内容
            List<TopicAppend> topicAppends = ServiceHolder.topicAppendService.findByTid(tid);
            //话题浏览次数+1
            topic.set("view", topic.getInt("view") + 1).update();
            //更新redis里的topic数据
            Cache cache = Redis.use();
            Topic _topic = cache.get(CacheEnum.topic.name() + tid);
            if (_topic != null) {
                _topic.set("view", _topic.getInt("view") + 1);
                cache.set(CacheEnum.topic.name() + tid, _topic);
            }
            //查询话题作者信息
            User authorinfo = ServiceHolder.userService.findByNickname(topic.getStr("author"));
            authorinfo.remove("receive_msg", "is_block", "third_access_token", "third_id", "channel", "expire_time",
                    "access_token");
            //查询回复
            List<Reply> replies = ServiceHolder.replyService.findByTopicId(tid);
            //查询收藏数量
            long collectCount = ServiceHolder.collectService.countByTid(tid);

            //渲染markdown
            if(mdrender) {
                topic.set("content", topic.marked(topic.getStr("content")));
                for(TopicAppend ta: topicAppends) {
                    ta.set("content", ta.marked(ta.getStr("content")));
                }
                for(Reply reply: replies) {
                    reply.set("content", reply.marked(reply.getStr("content")));
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("topic", topic);
            map.put("topicAppends", topicAppends);
            map.put("authorinfo", authorinfo);
            map.put("replies", replies);
            map.put("collectCount", collectCount);
            success(map);
        }
    }

    /**
     * 用户主页
     * @throws UnsupportedEncodingException
     */
    public void user() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        User currentUser = ServiceHolder.userService.findByNickname(nickname);
        if (currentUser == null) {
            error("用户不存在");
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("currentUser", currentUser);
            currentUser.remove("receive_msg", "is_block", "third_access_token", "third_id", "channel", "expire_time",
                    "access_token");
            Page<Topic> topicPage = ServiceHolder.topicService.pageByAuthor(1, 7, nickname);
            Page<Reply> replyPage = ServiceHolder.replyService.pageByAuthor(1, 7, nickname);
            //处理数据
            for(Topic topic: topicPage.getList()) {
                topic.remove("content", "is_delete", "show_status");
            }
            //渲染markdown
            if(mdrender) {
                for(Reply reply: replyPage.getList()) {
                    reply.set("content", reply.marked(reply.getStr("content")));
                }
            }
            map.put("topics", topicPage.getList());
            map.put("replies", replyPage.getList());
            success(map);
        }
    }

    /**
     * 发布话题
     * @throws UnsupportedEncodingException
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/create")
    public void create() throws UnsupportedEncodingException {
        Date now = new Date();
        String title = getPara("title");
        String content = getPara("content");
        String tab = getPara("tab");
        if (StrUtil.isBlank(Jsoup.clean(title, Whitelist.basic()))) {
            error(Constants.OP_ERROR_MESSAGE);
        } else if(StrUtil.isBlank(tab)) {
            error("请选择板块");
        } else {
            User user = getUserByToken();
            Topic topic = new Topic();
            topic.set("title", Jsoup.clean(title, Whitelist.basic()))
                    .set("content", content)
                    .set("tab", tab)
                    .set("in_time", now)
                    .set("last_reply_time", now)
                    .set("view", 0)
                    .set("author", user.get("nickname"))
                    .set("top", false)
                    .set("good", false)
                    .set("show_status", true)
                    .set("reply_count", 0)
                    .set("is_delete", false)
                    .save();
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            success();
        }
    }

    /**
     * 收藏话题
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/collect")
    public void collect() {
        Integer tid = getParaToInt("tid");
        if(tid == null) {
            error("话题ID不能为空");
        } else {
            Topic topic = ServiceHolder.topicService.findById(tid);
            if(topic == null) {
                error("收藏的话题不存在");
            } else {
                Date now = new Date();
                User user = getUserByToken();
                Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getInt("id"));
                if(collect == null) {
                    collect = new Collect();
                    collect.set("tid", tid)
                            .set("uid", user.getInt("id"))
                            .set("in_time", now)
                            .save();
                    //创建通知
                    ServiceHolder.notificationService.sendNotification(
                            user.getStr("nickname"),
                            topic.getStr("author"),
                            Constants.NotificationEnum.COLLECT.name(),
                            tid,
                            ""
                    );
                    //清理缓存
                    clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
                    clearCache(CacheEnum.collects.name() + user.getInt("id"));
                    clearCache(CacheEnum.collectcount.name() + tid);
                    clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
                    success();
                } else {
                    error("你已经收藏了此话题");
                }
            }
        }
    }

    /**
     * 取消收藏
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/topic/del_collect")
    public void del_collect() {
        Integer tid = getParaToInt("tid");
        User user = getUserByToken();
        Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getInt("id"));
        if(collect == null) {
            error("请先收藏");
        } else {
            collect.delete();
            clearCache(CacheEnum.usercollectcount.name() + user.getInt("id"));
            clearCache(CacheEnum.collects.name() + user.getInt("id"));
            clearCache(CacheEnum.collectcount.name() + tid);
            clearCache(CacheEnum.collect.name() + tid + "_" + user.getInt("id"));
            success();
        }
    }

    /**
     * 收藏话题
     */
    public void collects() throws UnsupportedEncodingException {
        String nickname = getPara(0);
        Boolean mdrender = getParaToBoolean("mdrender", true);
        if(StrUtil.isBlank(nickname)) {
            error("用户昵称不能为空");
        } else {
            User user = ServiceHolder.userService.findByNickname(nickname);
            if(user == null) {
                error("无效用户");
            } else {
                Page<Collect> page = ServiceHolder.collectService.findByUid(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getInt("id"));
                if(mdrender) {
                    for(Collect collect: page.getList()) {
                        collect.put("content", collect.marked(collect.get("content")));
                    }
                }
                success(page);
            }
        }
    }

    /**
     * 创建评论
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/reply/create")
    public void createReply() throws UnsupportedEncodingException {
        Integer tid = getParaToInt("tid");
        String content = getPara("content");
        if(tid == null || StrUtil.isBlank(content)) {
            error("话题ID和回复内容都不能为空");
        } else {
            Topic topic = ServiceHolder.topicService.findById(tid);
            if(topic == null) {
                error("话题不存在");
            } else {
                Date now = new Date();
                User user = getUserByToken();
                Reply reply = new Reply();
                reply.set("tid", tid)
                        .set("content", content)
                        .set("in_time", now)
                        .set("author", user.getStr("nickname"))
                        .set("is_delete", false)
                        .save();
                //topic reply_count++
                topic.set("reply_count", topic.getInt("reply_count") + 1)
                        .set("last_reply_time", now)
                        .set("last_reply_author", user.getStr("nickname"))
                        .update();
//                user.set("score", user.getInt("score") + 5).update();
                //发送通知
                //回复者与话题作者不是一个人的时候发送通知
                if(!user.getStr("nickname").equals(topic.getStr("author"))) {
                    ServiceHolder.notificationService.sendNotification(
                            user.getStr("nickname"),
                            topic.getStr("author"),
                            Constants.NotificationEnum.REPLY.name(),
                            tid,
                            content
                    );
                }
                //检查回复内容里有没有at用户,有就发通知
                List<String> atUsers = StrUtil.fetchUsers(content);
                for(String u: atUsers) {
                    if(!u.equals(topic.getStr("author"))) {
                        User _user = ServiceHolder.userService.findByNickname(u);
                        if (_user != null) {
                            ServiceHolder.notificationService.sendNotification(
                                    user.getStr("nickname"),
                                    _user.getStr("nickname"),
                                    Constants.NotificationEnum.AT.name(),
                                    tid,
                                    content
                            );
                        }
                    }
                }
                //清理缓存，保持数据最新
                clearCache(CacheEnum.topic.name() + tid);
                clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
                clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
                success();
            }
        }
    }

    /**
     * 未读通知数
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/notification/count")
    public void msgCount() {
        User user = getUserByToken();
        int count = ServiceHolder.notificationService.findNotReadCount(user.getStr("nickname"));
        success(count);
    }

    /**
     * 通知列表
     */
    @Before(ApiInterceptor.class)
    @ActionKey("/api/notifications")
    public void notifications() {
        Boolean mdrender = getParaToBoolean("mdrender", true);
        User user = getUserByToken();
        Page<Notification> page = ServiceHolder.notificationService.pageByAuthor(getParaToInt("p", 1), PropKit.getInt("pageSize"), user.getStr("nickname"));
        if(mdrender) {
            for (Notification notification : page.getList()) {
                notification.set("content", notification.marked(notification.get("content")));
            }
        }
        //将通知都设置成已读的
        ServiceHolder.notificationService.makeUnreadToRead(user.getStr("nickname"));
        success(page);
    }

}
