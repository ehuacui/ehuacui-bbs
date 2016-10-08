package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.dto.Result;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.IUserService;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
public class BaseController {

    @Autowired
    private IUserService userService;

    // 接口返回状态码
    private static final String CODE_SUCCESS = "200";
    private static final String CODE_FAILURE = "201";
    private static final String DESC_SUCCESS = "success";

    public Result success(Object object) {
        return new Result(CODE_SUCCESS, DESC_SUCCESS, object);
    }

    public Result error(String message) {
        return new Result(CODE_FAILURE, message, null);
    }

    public User getUser(HttpServletRequest request) {
        String user_cookie = WebUtil.getCookie(request, Constants.USER_ACCESS_TOKEN);
        if (StringUtil.notBlank(user_cookie)) {
            return userService.findByAccessToken(StringUtil.getDecryptToken(user_cookie));
        } else {
            return null;
        }
    }

    /**
     * 删除redis里的缓存
     */
    protected void clearCache(String key) {
    }

    protected String forward(String url) {
        return "forward:" + url;
    }

    protected String redirect(String url) {
        return "redirect:" + url;
    }

}
