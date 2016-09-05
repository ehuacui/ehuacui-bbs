package org.ehuacui.bbs.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.ehuacui.bbs.model.*;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.ext.route.ControllerBind;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.utils.SolrUtil;
import org.ehuacui.bbs.utils.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/topic", viewPath = "WEB-INF/page")
public class TopicController extends BaseController {

    /**
     * 话题详情
     */
    public void index() throws UnsupportedEncodingException {
        Integer tid = getParaToInt(0);
        Topic topic = ServiceHolder.topicService.findById(tid);
        if (topic == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            //处理一下置顶，精华
            topic.set_top(topic.getTop() ? "取消置顶" : "置顶");
            topic.set_good(topic.getGood() ? "取消精华" : "精华");
            //查询追加内容
            List<TopicAppend> topicAppends = ServiceHolder.topicAppendService.findByTid(tid);
            //话题浏览次数+1
            topic.setView(topic.getView() + 1);
            ServiceHolder.topicService.update(topic);
            /*
            //更新redis里的topic数据
            Cache cache = Redis.use();
            Topic _topic = cache.get(Constants.CacheEnum.topic.name() + tid);
            if (_topic != null) {
                _topic.setView(_topic.getView() + 1);
                cache.set(Constants.CacheEnum.topic.name() + tid, _topic);
            }
            */
            //查询版块名称
            Section section = ServiceHolder.sectionService.findByTab(topic.getTab());
            //查询话题作者信息
            User authorinfo = ServiceHolder.userService.findByNickname(topic.getAuthor());
            //查询作者其他话题
            List<Topic> otherTopics = ServiceHolder.topicService.findOtherTopicByAuthor(tid, topic.getAuthor(), 7);
            //查询回复
            Page<Reply> page = ServiceHolder.replyService.page(getParaToInt("p", 1), PropKit.getInt("replyPageSize"), tid);
            //查询收藏数量
            long collectCount = ServiceHolder.collectService.countByTid(tid);
            //查询当前用户是否收藏了该话题
            User user = getUser();
            if (user != null) {
                Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getId());
                setAttr("collect", collect);
            }
            setAttr("topic", topic);
            setAttr("topicAppends", topicAppends);
            setAttr("section", section);
            setAttr("authorinfo", authorinfo);
            setAttr("otherTopics", otherTopics);
            setAttr("page", page);
            setAttr("collectCount", collectCount);
            render("topic/detail.ftl");
        }
    }

    /**
     * 创建话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void create() throws UnsupportedEncodingException {
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("sections", ServiceHolder.sectionService.findByShowStatus(true));
            render("topic/create.ftl");
        } else if (method.equals("POST")) {
            Date now = new Date();
            String title = getPara("title");
            String content = getPara("content");
            if (StrUtil.isBlank(Jsoup.clean(title, Whitelist.basic()))) {
                renderText(Constants.OP_ERROR_MESSAGE);
            } else {
                String tab = getPara("tab");
                User user = getUser();
                Topic topic = new Topic();
                topic.setTitle(Jsoup.clean(title, Whitelist.basic()));
                topic.setContent(content);
                topic.setTab(tab);
                topic.setInTime(now);
                topic.setLastReplyTime(now);
                topic.setView(0);
                topic.setAuthor(user.getNickname());
                topic.setTop(false);
                topic.setGood(false);
                topic.setShowStatus(true);
                topic.setReplyCount(0);
                topic.setIsDelete(false);
                ServiceHolder.topicService.save(topic);

                //索引话题
                if (PropKit.getBoolean("solr.status")) {
                    SolrUtil solrUtil = new SolrUtil();
                    solrUtil.indexTopic(topic);
                }
                //给用户加分
//                user.set("score", user.getInt("score") + 5).update();
                //清理用户缓存
                clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
                clearCache(Constants.CacheEnum.useraccesstoken.name() + user.getAccessToken());
                redirect("/topic/" + topic.getId());
            }
        }
    }

    /**
     * 编辑话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void edit() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        Topic topic = ServiceHolder.topicService.findById(id);
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("sections", ServiceHolder.sectionService.findByShowStatus(true));
            setAttr("topic", topic);
            render("topic/edit.ftl");
        } else if (method.equals("POST")) {
            String tab = getPara("tab");
            String title = getPara("title");
            String content = getPara("content");
            topic.setTab(tab);
            topic.setTitle(Jsoup.clean(title, Whitelist.basic()));
            topic.setContent(content);
            ServiceHolder.topicService.update(topic);
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //清理缓存
            clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(topic.getAuthor(), "utf-8"));
            clearCache(Constants.CacheEnum.topic.name() + id);
            redirect("/topic/" + topic.getId());
        }
    }

    /**
     * 话题追加
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class
    })
    public void append() {
        String method = getRequest().getMethod();
        Integer tid = getParaToInt(0);
        Topic topic = ServiceHolder.topicService.findById(tid);
        User user = getUser();
        if (topic.getAuthor().equals(user.getNickname())) {
            if (method.equals("GET")) {
                setAttr("topic", topic);
                render("topic/append.ftl");
            } else if (method.equals("POST")) {
                Date now = new Date();
                String content = getPara("content");
                TopicAppend topicAppend = new TopicAppend();
                topicAppend.setTid(tid);
                topicAppend.setContent(content);
                topicAppend.setInTime(now);
                topicAppend.setIsDelete(false);
                ServiceHolder.topicAppendService.save(topicAppend);
                //索引话题
                if (PropKit.getBoolean("solr.status")) {
                    topic.setContent(topic.getContent() + "\n" + content);
                    SolrUtil solrUtil = new SolrUtil();
                    solrUtil.indexTopic(topic);
                }
                //清理缓存
                clearCache(Constants.CacheEnum.topicappends.name() + tid);
                redirect("/topic/" + tid);
            }
        } else {
            renderText(Constants.OP_ERROR_MESSAGE);
        }
    }

    /**
     * 编辑追加的内容
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void appendedit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        TopicAppend topicAppend = ServiceHolder.topicAppendService.findById(id);
        Topic topic = ServiceHolder.topicService.findById(topicAppend.getTid());
        if (method.equals("GET")) {
            setAttr("topicAppend", topicAppend);
            setAttr("topic", topic);
            render("topic/appendedit.ftl");
        } else if (method.equals("POST")) {
            String content = getPara("content");
            topicAppend.setContent(content);
            ServiceHolder.topicAppendService.update(topicAppend);
            //索引话题
            if (PropKit.getBoolean("solr.status")) {
                topic.setContent(topic.getContent() + "\n" + content);
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //清理缓存
            clearCache(Constants.CacheEnum.topicappends.name() + topic.getId());
            redirect("/topic/" + topic.getId());
        }
    }

    /**
     * 删除话题
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class,
            Tx.class
    })
    public void delete() throws UnsupportedEncodingException {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            ServiceHolder.topicAppendService.deleteByTid(id);
            ServiceHolder.replyService.deleteByTid(id);
            Topic topic = ServiceHolder.topicService.findById(id);
            //删除用户积分
//            User user = ServiceHolder.userService.findByNickname(topic.getStr("author"));
//            Integer score = user.getInt("score");
//            score = score > 7 ? score - 7 : 0;
//            user.set("score", score).update();
            //删除话题（非物理删除）
            ServiceHolder.topicService.deleteById(id);
            //删除索引
            if (PropKit.getBoolean("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexDelete(String.valueOf(id));
            }
            //清理缓存
//            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//            clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
            redirect("/");
        }
    }

    /**
     * 置顶
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void top() {
        Integer id = getParaToInt("id");
        ServiceHolder.topicService.top(id);
        clearCache(Constants.CacheEnum.topic.name() + id);
        redirect("/topic/" + id);
    }

    /**
     * 设置精华
     */
    @Before({
            UserInterceptor.class,
            UserStatusInterceptor.class,
            PermissionInterceptor.class
    })
    public void good() {
        Integer id = getParaToInt("id");
        ServiceHolder.topicService.good(id);
        clearCache(Constants.CacheEnum.topic.name() + id);
        redirect("/topic/" + id);
    }
}
