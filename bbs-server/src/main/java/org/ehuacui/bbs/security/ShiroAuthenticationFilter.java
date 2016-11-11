package org.ehuacui.bbs.security;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 服务报告登录认证
 * Created by jianwei.zhou on 2016/9/13.
 */
public class ShiroAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(ShiroAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isLoginRequest(httpServletRequest, httpServletResponse)) {
            if (isLoginSubmission(httpServletRequest, httpServletResponse)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected. Attempting to execute login.");
                }
                return executeLogin(httpServletRequest, httpServletResponse);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                // allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }
            // 不是ajax请求
            if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))) {
                saveRequestAndRedirectToLogin(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = httpServletResponse.getWriter();
                String strJsonFormat = "{\"code\": \"%d\",\"url\": \"%s\",\"msg\": \"%s\"}";
                //前端js依据loginHtml字符串判断是否为登录界面故没有登录也返回此信息
                out.println(String.format(strJsonFormat, 401, "/toLogin", "loginHtml"));
                out.flush();
                out.close();
            }
            return false;
        }
    }
}
