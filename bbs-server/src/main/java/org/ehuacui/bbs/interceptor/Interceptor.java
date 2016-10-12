package org.ehuacui.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * Created by Administrator on 2016/9/13.
 */
public interface Interceptor {

    void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
