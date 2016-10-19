package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.service.ISearchService;
import org.ehuacui.bbs.service.ISectionService;
import org.ehuacui.bbs.service.ITopicService;
import org.ehuacui.bbs.template.FormatDate;
import org.ehuacui.bbs.template.GetAvatarByNickname;
import org.ehuacui.bbs.template.GetNameByTab;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${solr.status}")
    private String solrStatus;
    @Value("${pageSize}")
    private Integer pageSize;
    @Value("${cookie.domain}")
    private String cookieDomain;
    @Value("${static.path}")
    private String staticPath;
    @Value("${upload.type}")
    private String uploadType;
    @Value("${file.domain}")
    private String fileDomain;
    @Value("${qiniu.url}")
    private String qiniuURL;

    @Autowired
    private ISectionService sectionService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private ISearchService searchService;

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, @RequestParam(value = "tab", defaultValue = "all") String tab,
                        @RequestParam(value = "p", defaultValue = "1") Integer p) {
        /* 隐藏版块
        if (!tab.equals("all") && !tab.equals("good") && !tab.equals("noreply")) {
            Section section = sectionService.findByTab(tab);
            request.setAttribute("sectionName", section.getName());
        } else {
            request.setAttribute("sectionName", "版块");
        }
        */
        PageDataBody<Topic> page = topicService.page(p, pageSize, tab);
        request.setAttribute("tab", tab);
        request.setAttribute("sections", sectionService.findByShowStatus(true));
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
        WebUtil.removeCookie(response, Constants.USER_ACCESS_TOKEN, "/", cookieDomain);
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
            String fileName = file.getOriginalFilename();
            File targetFile = new File(staticPath, fileName);
            File parentFile = targetFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            //保存
            file.transferTo(targetFile);
            String url = "";
            if (uploadType.equals("local")) {
                url = fileDomain + "/static/upload/" + fileName;
            } else if (uploadType.equals("qiniu")) {
                // 将本地文件上传到七牛,并删除本地文件
                String filePath = staticPath + fileName;
                Map map = upload(filePath);
                targetFile.delete();
                url = qiniuURL + "/" + map.get("key");
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
        if (solrStatus.equalsIgnoreCase("true")) {
            searchService.indexAll();
        }
        return redirect("/");
    }

    /**
     * 搜索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") Integer p, @RequestParam("q") String q) {
        if (solrStatus.equalsIgnoreCase("true")) {
            PageDataBody page = searchService.indexQuery(p, q);
            request.setAttribute("q", q);
            request.setAttribute("page", page);
        }
        return "search";
    }

    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/delete-all-index", method = RequestMethod.GET)
    public String deleteAllIndex() {
        if (solrStatus.equalsIgnoreCase("true")) {
            searchService.deleteAll();
        }
        return redirect("/");
    }

    /**
     * 积分前100名用户
     */
    @RequestMapping(value = "/top/100", method = RequestMethod.GET)
    public String top100() {
        return "top100";
    }

    /**
     * 清理缓存
     */
    @BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() {
        return redirect("/");
    }

}