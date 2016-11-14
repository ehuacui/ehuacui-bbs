package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Section;
import org.ehuacui.bbs.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/section")
@BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
public class SectionController extends BaseController {

    @Autowired
    private SectionService sectionService;

    /**
     * 板块列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request) {
        request.setAttribute("sections", sectionService.findAll());
        return "section/list";
    }

    /**
     * 改变板块显示状态
     */
    @RequestMapping(value = "/change-show-status", method = RequestMethod.GET)
    public String changeShowStatus(@RequestParam("id") Integer id) {
        Section section = sectionService.findById(id);
        section.setShowStatus(!section.getShowStatus());
        sectionService.update(section);
        return redirect("/section/list");
    }

    /**
     * 删除板块
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id) {
        sectionService.deleteById(id);
        return redirect("/section/list");
    }

    /**
     * 添加板块
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "section/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam("name") String name, @RequestParam("tab") String tab,
                      @RequestParam("showStatus") Integer showStatus) {
        Section section = new Section();
        section.setName(name);
        section.setTab(tab);
        section.setShowStatus(showStatus == 1);
        section.setDisplayIndex(99);
        section.setPid(0);//默认为顶级节点
        section.setDefaultShow(false);
        sectionService.save(section);
        return redirect("/section/list");
    }

    /**
     * 编辑板块
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @RequestParam("id") Integer id) {
        Section section = sectionService.findById(id);
        request.setAttribute("section", section);
        return "section/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam("id") Integer id, @RequestParam("name") String name,
                       @RequestParam("tab") String tab, @RequestParam("showStatus") Integer showStatus) {
        Section section = sectionService.findById(id);
        section.setId(id);
        section.setName(name);
        section.setTab(tab);
        section.setShowStatus(showStatus == 1);
        section.setPid(0);//默认为顶级节点
        sectionService.update(section);
        return redirect("/section/list");
    }
}
