package org.ehuacui.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BasicInterceptor
 * Created by Administrator on 2016/9/13.
 */
public class BasicInterceptor implements Interceptor {
    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("=========BasicInterceptor==========");
    }
}
