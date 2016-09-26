package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.interceptor.UserStatusInterceptor;
import org.ehuacui.bbs.model.*;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.Marked;
import org.ehuacui.bbs.template.MarkedNotAt;
import org.ehuacui.bbs.utils.ResourceUtil;
import org.ehuacui.bbs.utils.SolrUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

    /**
     * 话题详情
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request,
                        @RequestParam("tid") Integer tid,
                        @RequestParam(value = "p", defaultValue = "1") Integer p) {
        Topic topic = ServiceHolder.topicService.findById(tid);
        //处理一下置顶，精华
        topic.setIsTop(topic.getTop() ? "取消置顶" : "置顶");
        topic.setIsGood(topic.getGood() ? "取消精华" : "精华");
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
        Integer replyPageSize = ResourceUtil.getWebConfigIntegerValueByKey("replyPageSize");
        Page<Reply> page = ServiceHolder.replyService.page(p, replyPageSize, tid);
        //查询收藏数量
        long collectCount = ServiceHolder.collectService.countByTid(tid);
        //查询当前用户是否收藏了该话题
        User user = getUser(request);
        if (user != null) {
            Collect collect = ServiceHolder.collectService.findByTidAndUid(tid, user.getId());
            request.setAttribute("collect", collect);
        }
        request.setAttribute("topic", topic);
        request.setAttribute("topicAppends", topicAppends);
        request.setAttribute("section", section);
        request.setAttribute("authorinfo", authorinfo);
        request.setAttribute("otherTopics", otherTopics);
        request.setAttribute("page", page);
        request.setAttribute("collectCount", collectCount);
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        request.setAttribute("marked", new Marked());
        request.setAttribute("markedNotAt", new MarkedNotAt());
        request.setAttribute("formatDate", new FormatDate());
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        return "topic/detail";
    }

    /**
     * 创建话题
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(HttpServletRequest request) {
        request.setAttribute("sections", ServiceHolder.sectionService.findByShowStatus(true));
        return "topic/create";
    }

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(HttpServletRequest request,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("tab") String tab) throws UnsupportedEncodingException {
        Date now = new Date();
        if (StringUtil.isBlank(Jsoup.clean(title, Whitelist.basic()))) {
            //renderText(Constants.OP_ERROR_MESSAGE);
            return "";
        } else {
            User user = getUser(request);
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
            if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //给用户加分
            //user.set("score", user.getInt("score") + 5).update();
            //清理用户缓存
            clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(user.getNickname(), "utf-8"));
            clearCache(Constants.CacheEnum.useraccesstoken.name() + user.getAccessToken());
            return redirect("/topic/" + topic.getId());
        }
    }

    /**
     * 编辑话题
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @RequestParam("id") Integer id) {
        Topic topic = ServiceHolder.topicService.findById(id);
        request.setAttribute("sections", ServiceHolder.sectionService.findByShowStatus(true));
        request.setAttribute("topic", topic);
        return "topic/edit";
    }

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam("id") Integer id,
                       @RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @RequestParam("tab") String tab) throws UnsupportedEncodingException {
        Topic topic = ServiceHolder.topicService.findById(id);
        topic.setTab(tab);
        topic.setTitle(Jsoup.clean(title, Whitelist.basic()));
        topic.setContent(content);
        ServiceHolder.topicService.update(topic);
        //索引话题
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.indexTopic(topic);
        }
        //清理缓存
        clearCache(Constants.CacheEnum.usernickname.name() + URLEncoder.encode(topic.getAuthor(), "utf-8"));
        clearCache(Constants.CacheEnum.topic.name() + id);
        return redirect("/topic/" + topic.getId());
    }

    /**
     * 话题追加
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/append", method = RequestMethod.GET)
    public String append(HttpServletRequest request, @RequestParam("tid") Integer tid) {
        Topic topic = ServiceHolder.topicService.findById(tid);
        User user = getUser(request);
        if (topic.getAuthor().equals(user.getNickname())) {
            request.setAttribute("topic", topic);
            return "topic/append";
        } else {
            return "";
        }
    }

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class})
    @RequestMapping(value = "/append", method = RequestMethod.POST)
    public String append(HttpServletRequest request, @RequestParam("tid") Integer tid, @RequestParam("content") String content) {
        Topic topic = ServiceHolder.topicService.findById(tid);
        User user = getUser(request);
        if (topic.getAuthor().equals(user.getNickname())) {
            Date now = new Date();
            TopicAppend topicAppend = new TopicAppend();
            topicAppend.setTid(tid);
            topicAppend.setContent(content);
            topicAppend.setInTime(now);
            topicAppend.setIsDelete(false);
            ServiceHolder.topicAppendService.save(topicAppend);
            //索引话题
            if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
                topic.setContent(topic.getContent() + "\n" + content);
                SolrUtil solrUtil = new SolrUtil();
                solrUtil.indexTopic(topic);
            }
            //清理缓存
            clearCache(Constants.CacheEnum.topicappends.name() + tid);
            return redirect("/topic/" + tid);
        } else {
            return "";
        }
    }

    /**
     * 编辑追加的内容
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/appendedit", method = RequestMethod.GET)
    public String appendedit(HttpServletRequest request, @RequestParam("id") Integer id) {
        TopicAppend topicAppend = ServiceHolder.topicAppendService.findById(id);
        Topic topic = ServiceHolder.topicService.findById(topicAppend.getTid());
        request.setAttribute("topicAppend", topicAppend);
        request.setAttribute("topic", topic);
        return "topic/appendedit";
    }

    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/appendedit", method = RequestMethod.POST)
    public String appendedit(@RequestParam("id") Integer id, String content) {
        TopicAppend topicAppend = ServiceHolder.topicAppendService.findById(id);
        Topic topic = ServiceHolder.topicService.findById(topicAppend.getTid());
        topicAppend.setContent(content);
        ServiceHolder.topicAppendService.update(topicAppend);
        //索引话题
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
            topic.setContent(topic.getContent() + "\n" + content);
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.indexTopic(topic);
        }
        //清理缓存
        clearCache(Constants.CacheEnum.topicappends.name() + topic.getId());
        return redirect("/topic/" + topic.getId());
    }

    /**
     * 删除话题
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id) {
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
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.indexDelete(String.valueOf(id));
        }
        //清理缓存
//            clearCache(CacheEnum.usernickname.name() + URLEncoder.encode(user.getStr("nickname"), "utf-8"));
//            clearCache(CacheEnum.useraccesstoken.name() + user.getStr("access_token"));
        return redirect("/");
    }

    /**
     * 置顶
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String top(@RequestParam("id") Integer id) {
        ServiceHolder.topicService.top(id);
        clearCache(Constants.CacheEnum.topic.name() + id);
        return redirect("/topic/" + id);
    }

    /**
     * 设置精华
     */
    @BeforeAdviceController({UserInterceptor.class, UserStatusInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/good", method = RequestMethod.GET)
    public String good(@RequestParam("id") Integer id) {
        ServiceHolder.topicService.good(id);
        clearCache(Constants.CacheEnum.topic.name() + id);
        return redirect("/topic/" + id);
    }
}
