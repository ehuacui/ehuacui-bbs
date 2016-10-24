package org.ehuacui.bbs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * BusinessExceptionHandler.java 拦截所有Controller抛出的异常，以文件的方式记录到系统日志中<br />
 *
 * @author jianwei.zhou
 * @version 1.0 2016-4-27
 */
public class BusinessExceptionHandler implements HandlerExceptionResolver {

    private final static Logger logger = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    //public static final String xmlHttpRequestHeaderStr = "XMLHttpRequest";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj,
                                         Exception ex) {

        Map<String, Object> map = new HashMap<String, Object>();

        String errorMsg;
        // 把漏网的异常信息记入日志
        // 对Spring初始化的一些异常进行处理
        if (ex instanceof BusinessException) {
            errorMsg = ex.getMessage();
            logger.error(ex.getMessage(), ex);
        } else {
            // 其他的异常做为系统编码或者未知异常处理
            errorMsg = "服务器竟然出小差了......";
            String lastAccessUrl = "URI:" + request.getRequestURI() + " \n URL:" + request.getRequestURL() + "?" + request.getQueryString();
            logger.error("UnKnown Exception \n URL/URI Access Info--> \n" + lastAccessUrl, ex);
        }

        String exStackMsg = "";// 异常堆栈信息
        StackTraceElement[] elements = ex.getStackTrace();
        if (elements != null && elements.length > 0) {
            for (int i = 0; i < 5; i++) { // 目前只获取异常堆栈信息(深度为5)
                exStackMsg = exStackMsg + "\t" + elements[i].toString() + "\n";
            }
        }

        String bugMessage = errorMsg + "\n" + exStackMsg;

        // 看看是否是Ajax请求
        if (request.getHeader("X-Requested-With") != null &&
                request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            map.put("ajax", true);
        }
        // 将错误提示信息传递给view
        map.put("errorMsg", errorMsg);
        map.put("bugMsg", bugMessage);

        return new ModelAndView("error", map);
    }
}
