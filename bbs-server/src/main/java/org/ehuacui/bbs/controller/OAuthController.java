package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.dto.Constants;
import org.ehuacui.bbs.model.OAuthUserInfo;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.model.UserRole;
import org.ehuacui.bbs.service.OAuthService;
import org.ehuacui.bbs.service.RoleService;
import org.ehuacui.bbs.service.UserRoleService;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.utils.DateUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.ehuacui.bbs.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Random;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController extends BaseController {

    @Value("${cookie.domain}")
    private String cookieDomain;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Resource(name = "OAuthServiceImpl")
    private OAuthService gitHubOAuthService;

    private static final String STATE = "third_login_state";

    /**
     * github登录
     */
    @RequestMapping(value = "/github/login", method = RequestMethod.GET)
    public void githubLogin(HttpServletResponse response) throws IOException {
        String secretState = "secret" + new Random().nextInt(999_999);
        WebUtil.setCookie(response, STATE, secretState, 5 * 60, "/", cookieDomain, true);
        String authorizationUrl = gitHubOAuthService.getAuthorizationUrl(secretState);
        response.sendRedirect(authorizationUrl);
    }

    /**
     * github登录成功后回调
     */
    @RequestMapping(value = "/github/callback", method = RequestMethod.GET)
    public String githubCallback(@RequestParam("code") String code, @RequestParam("state") String state,
                                 @RequestParam(value = "callback", required = false, defaultValue = "/") String callback,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieState = WebUtil.getCookie(request, STATE);
        if (state.equalsIgnoreCase(cookieState)) {
            OAuthUserInfo oAuthUserInfo = gitHubOAuthService.getOAuthUserInfo(code, state);
            if (oAuthUserInfo == null) {
                return redirect("/");
            }
            String githubId = oAuthUserInfo.getOauthUserId();
            String login = oAuthUserInfo.getLoginName();
            String avatar_url = oAuthUserInfo.getAvatarUrl();
            String email = oAuthUserInfo.getUserEmail();
            String html_url = oAuthUserInfo.getHomeUrl();
            Date now = new Date();
            String access_token = StringUtil.getUUID();
            //User user = userService.findByThirdId(githubId);
            User user = null;
            boolean flag = true;
            if (user == null) {
                user = new User();
                user.setInTime(now);
                user.setAccessToken(access_token);
                user.setScore(0);
                //user.setThirdId(githubId);
                user.setIsBlock(false);
                //user.setChannel(Constants.LoginEnum.Github.name());
                user.setReceiveMsg(true);//邮箱接收社区消息
                flag = false;
            }
            user.setNickname(login);
            user.setAvatar(avatar_url);
            user.setEmail(email);
            user.setUrl(html_url);
            user.setExpireTime(DateUtil.getDateAfter(now, 30));//30天后过期,要重新认证
            if (flag) {
                userService.update(user);
            } else {
                userService.save(user);
                //新注册的用户角色都是普通用户
                Role role = roleService.findByName("user");
                if (role != null) {
                    UserRole userRole = new UserRole();
                    userRole.setUid(user.getId());
                    userRole.setRid(role.getId());
                    userRoleService.save(userRole);
                }
            }
            WebUtil.setCookie(response, Constants.USER_ACCESS_TOKEN,
                    StringUtil.getEncryptionToken(user.getAccessToken()),
                    30 * 24 * 60 * 60, "/", cookieDomain, true);
            if (StringUtil.notBlank(callback)) {
                try {
                    callback = URLDecoder.decode(callback, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    callback = null;
                }
            }
            return redirect(StringUtil.notBlank(callback) ? callback : "/");
        } else {
            return redirect("/");
        }
    }

}
