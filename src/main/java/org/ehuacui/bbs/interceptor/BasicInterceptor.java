package org.ehuacui.bbs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * BasicInterceptor
 * Created by Administrator on 2016/9/13.
 */
public class BasicInterceptor implements Interceptor {
    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("=========BasicInterceptor==========");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        String strJsonFormat = "{\"code\":%d,\"url\": \"%s\"}";
        out.write(String.format(strJsonFormat, 400, "/report/home").getBytes());
        out.flush();
        out.close();
    }
}
