package org.ehuacui.bbs.controller;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Section;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.utils.QiniuUploadUtil;
import org.ehuacui.bbs.utils.SolrUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
public class IndexController extends BaseController {

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @RequestParam(value = "tab", defaultValue = "all") String tab,
                        @RequestParam(value = "p", defaultValue = "1") Integer p) {
        if (!tab.equals("all") && !tab.equals("good") && !tab.equals("noreply")) {
            Section section = ServiceHolder.sectionService.findByTab(tab);
            request.setAttribute("sectionName", section.getName());
        } else {
            request.setAttribute("sectionName", "版块");
        }
        Page<Topic> page = ServiceHolder.topicService.page(p, PropKit.getInt("pageSize", 20), tab);
        request.setAttribute("tab", tab);
        request.setAttribute("sections", ServiceHolder.sectionService.findByShowStatus(true));
        request.setAttribute("page", page);
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        request.setAttribute("getNameByTab", new GetNameByTab());
        request.setAttribute("formatDate", new FormatDate());
        return "index_ftl";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (getUser(request) == null) {
            return "login_ftl";
        } else {
            return redirect("/");
        }
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", PropKit.get("cookie.domain"));
        return redirect("/");
    }

    /**
     * 关于
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about_ftl";
    }

    /**
     * 上传
     */
    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(@RequestParam("file") MultipartFile file) {
        try {
            String path = PropKit.get("static.path");
            String fileName = file.getOriginalFilename();
            File targetFile = new File(path, fileName);
            File parentFile = targetFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            //保存
            file.transferTo(targetFile);
            String url = "";
            if (PropKit.get("upload.type").equals("local")) {
                url = PropKit.get("file.domain") + "/static/upload/" + fileName;
            } else if (PropKit.get("upload.type").equals("qiniu")) {
                // 将本地文件上传到七牛,并删除本地文件
                String filePath = path + fileName;
                Map map = new QiniuUploadUtil().upload(filePath);
                targetFile.delete();
                url = PropKit.get("qiniu.url") + "/" + map.get("key");
            }
            success(url);
        } catch (Exception e) {
            e.printStackTrace();
            error("图片上传失败,再试一次吧");
        }
    }

    /**
     * 索引所有话题
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/solr", method = RequestMethod.GET)
    public String solr() {
        if (PropKit.getBoolean("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.indexAll();
        }
        return redirect("/");
    }

    /**
     * 搜索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam("q") String q) {
        if (PropKit.getBoolean("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            Page page = solrUtil.indexQuery(p, q);
            request.setAttribute("q", q);
            request.setAttribute("page", page);
        }
        return "search_ftl";
    }

    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete-all-index", method = RequestMethod.GET)
    public String deleteAllIndex() {
        if (PropKit.getBoolean("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.deleteAll();
        }
        return redirect("/");
    }

    /**
     * 积分前100名用户
     */
    @RequestMapping(value = "/top100", method = RequestMethod.GET)
    public String top100() {
        return "top100_ftl";
    }

    /**
     * 清理缓存
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() {
        Cache cache = Redis.use();
        if (cache != null) {
            cache.getJedis().flushDB();
        }
        return redirect("/");
    }

}