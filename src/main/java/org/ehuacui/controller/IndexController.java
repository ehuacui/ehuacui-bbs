package org.ehuacui.controller;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.upload.UploadFile;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.Page;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.PermissionInterceptor;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.model.Section;
import org.ehuacui.model.Topic;
import org.ehuacui.utils.QiniuUpload;
import org.ehuacui.utils.SolrUtil;
import org.ehuacui.utils.StrUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@ControllerBind(controllerKey = "/", viewPath = "WEB-INF/page")
public class IndexController extends BaseController {

    /**
     * 首页
     */
    public void index() {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            tab = "all";
        }
        if (!tab.equals("all") && !tab.equals("good") && !tab.equals("noreply")) {
            Section section = ServiceHolder.sectionService.findByTab(tab);
            setAttr("sectionName", section.getName());
        } else {
            setAttr("sectionName", "版块");
        }
        Page<Topic> page = ServiceHolder.topicService.page(getParaToInt("p", 1), PropKit.getInt("pageSize", 20), tab);
        setAttr("tab", tab);
        setAttr("sections", ServiceHolder.sectionService.findByShowStatus(true));
        setAttr("page", page);
        render("index.ftl");
    }

    /**
     * 登录
     */
    public void login() {
        if (getUser() == null) {
            render("login.ftl");
        } else {
            redirect("/");
        }
    }

    /**
     * 登出
     */
    public void logout() {
        removeCookie(Constants.USER_ACCESS_TOKEN, "/", PropKit.get("cookie.domain"));
        redirect("/");
    }

    /**
     * 关于
     */
    public void about() {
        render("about.ftl");
    }

    /**
     * 上传
     */
    @Before(UserInterceptor.class)
    public void upload() {
        try {
            List<UploadFile> uploadFiles = getFiles(PropKit.get("static.path"));
            List<String> urls = new ArrayList<>();
            for (UploadFile uf : uploadFiles) {
                String url = "";
                if (PropKit.get("upload.type").equals("local")) {
                    url = PropKit.get("file.domain") + "/static/upload/" + uf.getFileName();
                    urls.add(url);
                } else if (PropKit.get("upload.type").equals("qiniu")) {
                    // 将本地文件上传到七牛,并删除本地文件
                    String filePath = uf.getUploadPath() + uf.getFileName();
                    Map map = new QiniuUpload().upload(filePath);
                    new File(filePath).delete();
                    url = PropKit.get("qiniu.url") + "/" + map.get("key");
                    urls.add(url);
                }
            }
            success(urls);
        } catch (Exception e) {
            e.printStackTrace();
            error("图片上传失败,再试一次吧");
        }
    }

    /**
     * 索引所有话题
     */
    @Before({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void solr() {
        if (PropKit.getBoolean("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.indexAll();
            redirect("/");
        } else {
            renderText("网站没有开启搜索功能!");
        }
    }

    /**
     * 搜索
     */
    public void search() {
        if (PropKit.getBoolean("solr.status")) {
            Integer pageNumber = getParaToInt("p", 1);
            String q = getPara("q");
            SolrUtil solrUtil = new SolrUtil();
            Page page = solrUtil.indexQuery(pageNumber, q);
            setAttr("q", q);
            setAttr("page", page);
            render("search.ftl");
        } else {
            renderText("网站没有开启搜索功能!");
        }
    }

    @Before({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void deleteallindex() {
        if (PropKit.getBoolean("solr.status")) {
            SolrUtil solrUtil = new SolrUtil();
            solrUtil.deleteAll();
            redirect("/");
        } else {
            renderText("网站没有开启搜索功能!");
        }
    }

    /**
     * 积分前100名用户
     */
    public void top100() {
        render("top100.ftl");
    }

    /**
     * 捐赠
     */
    public void donate() {
        render("donate.ftl");
    }

    /**
     * API
     */
    public void api() {
        render("api.ftl");
    }

    /**
     * 清理缓存
     */
    @Before({
            UserInterceptor.class,
            PermissionInterceptor.class
    })
    public void clear() {
        Cache cache = Redis.use();
        if (cache != null) {
            cache.getJedis().flushDB();
        }
        redirect("/");
    }

}