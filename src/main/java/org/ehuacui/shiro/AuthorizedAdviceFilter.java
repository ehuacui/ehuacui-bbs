package org.ehuacui.shiro;

import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jianwei.zhou
 */
public class AuthorizedAdviceFilter extends AdviceFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //System.out.println("====预处理/前置处理");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String servletPath = httpServletRequest.getServletPath();
        //返回false将中断后续拦截器链的执行
        if (ShiroUtils.isPermitted(PermissionCode.REPORT_TOP)) {
            return true;
        } else if ((ShiroUtils.hasRole(RoleCode.ROLE_ADMINISTRATOR) ||
                ShiroUtils.hasRole(RoleCode.ROLE_STAFF_OPTS) ||
                ShiroUtils.hasRole(RoleCode.ROLE_STAFF_IT_ENGENEER)) &&
                (servletPath.startsWith("/reportRole") || servletPath.startsWith("/reportInfo"))) {
            return true;
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect("/");
            return false;
        }
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        //System.out.println("====后处理/后置返回处理");
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        //System.out.println("====完成处理/后置最终处理");
    }
}
