package org.ehuacui.controller;

import com.jfinal.aop.Before;
import org.ehuacui.common.BaseController;
import org.ehuacui.common.Constants;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.ext.route.ControllerBind;
import org.ehuacui.interceptor.PermissionInterceptor;
import org.ehuacui.interceptor.UserInterceptor;
import org.ehuacui.model.Section;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Before({
        UserInterceptor.class,
        PermissionInterceptor.class
})
@ControllerBind(controllerKey = "/section", viewPath = "WEB-INF/page")
public class SectionController extends BaseController {

    /**
     * 板块列表
     */
    public void list() {
        setAttr("sections", ServiceHolder.sectionService.findAll());
        render("section/list.ftl");
    }

    /**
     * 改变板块显示状态
     */
    public void changeshowstatus() {
        Integer id = getParaToInt("id");
        Section section = ServiceHolder.sectionService.findById(id);
        section.setShowStatus(!section.getShowStatus());
        ServiceHolder.sectionService.update(section);
        clearCache(Constants.CacheEnum.sections.name() + true);
        clearCache(Constants.CacheEnum.sections.name() + false);
        clearCache(Constants.CacheEnum.section.name() + section.getTab());
        redirect("/section/list");
    }

    /**
     * 删除板块
     */
    public void delete() {
        Integer id = getParaToInt("id");
        Section section = ServiceHolder.sectionService.findById(id);
        ServiceHolder.sectionService.deleteById(id);
        clearCache(Constants.CacheEnum.sections.name() + true);
        clearCache(Constants.CacheEnum.sections.name() + false);
        clearCache(Constants.CacheEnum.section.name() + section.getTab());
        redirect("/section/list");
    }

    /**
     * 添加板块
     */
    public void add() {
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            render("section/add.ftl");
        } else if (method.equals("POST")) {
            String name = getPara("name");
            String tab = getPara("tab");
            Integer showStatus = getParaToInt("showStatus");
            Section section = new Section();
            section.setName(name);
            section.setTab(tab);
            section.setShowStatus(showStatus == 1);
            section.setDisplayIndex(99);
            section.setDefaultShow(false);
            ServiceHolder.sectionService.save(section);
            redirect("/section/list");
        }
    }

    /**
     * 编辑板块
     */
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        Section section = ServiceHolder.sectionService.findById(id);
        if (method.equals("GET")) {
            setAttr("section", section);
            render("section/edit.ftl");
        } else if (method.equals("POST")) {
            String name = getPara("name");
            String tab = getPara("tab");
            Integer showStatus = getParaToInt("showStatus");
            section.setId(id);
            section.setName(name);
            section.setTab(tab);
            section.setShowStatus(showStatus == 1);
            ServiceHolder.sectionService.update(section);
            redirect("/section/list");
        }
    }
}
