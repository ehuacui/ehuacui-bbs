package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.*;
import org.ehuacui.bbs.model.Section;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.utils.QiniuUploadUtil;
import org.ehuacui.bbs.utils.ResourceUtil;
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
@BeforeAdviceController(BasicInterceptor.class)
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
        Page<Topic> page = ServiceHolder.topicService.page(p, ResourceUtil.getWebConfigIntegerValueByKey("pageSize", 20), tab);
        request.setAttribute("tab", tab);
        request.setAttribute("sections", ServiceHolder.sectionService.findByShowStatus(true));
        request.setAttribute("page", page);
        request.setAttribute("getAvatarByNickname", new GetAvatarByNickname());
        request.setAttribute("getNameByTab", new GetNameByTab());
        request.setAttribute("formatDate", new FormatDate());
        return "index";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (getUser(request) == null) {
            return "login";
        } else {
            return redirect("/");
        }
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", ResourceUtil.getWebConfigValueByKey("cookie.domain"));
        return redirect("/");
    }

    /**
     * 关于
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    /**
     * 上传
     */
    @BeforeAdviceController(UserInterceptor.class)
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload(@RequestParam("file") MultipartFile file) {
        try {
            String path = ResourceUtil.getWebConfigValueByKey("static.path");
            String fileName = file.getOriginalFilename();
            File targetFile = new File(path, fileName);
            File parentFile = targetFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            //保存
            file.transferTo(targetFile);
            String url = "";
            if (ResourceUtil.getWebConfigValueByKey("upload.type").equals("local")) {
                url = ResourceUtil.getWebConfigValueByKey("file.domain") + "/static/upload/" + fileName;
            } else if (ResourceUtil.getWebConfigValueByKey("upload.type").equals("qiniu")) {
                // 将本地文件上传到七牛,并删除本地文件
                String filePath = path + fileName;
                Map map = new QiniuUploadUtil().upload(filePath);
                targetFile.delete();
                url = ResourceUtil.getWebConfigValueByKey("qiniu.url") + "/" + map.get("key");
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
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
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
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            Page page = solrUtil.indexQuery(p, q);
            request.setAttribute("q", q);
            request.setAttribute("page", page);
        }
        return "search";
    }

    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete-all-index", method = RequestMethod.GET)
    public String deleteAllIndex() {
        if (ResourceUtil.getWebConfigBooleanValueByKey("solr.status")) {
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
        return "top100";
    }

    /**
     * 清理缓存
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() {
        /*
        Cache cache = Redis.use();
        if (cache != null) {
            cache.getJedis().flushDB();
        }
        */
        return redirect("/");
    }

}