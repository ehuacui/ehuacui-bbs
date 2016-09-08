package org.ehuacui.bbs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * SpringMVC多视图解析器
 * Created by jianwei.zhou on 2016/7/2.
 */
public class MultiViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(MultiViewResolver.class);
    /***
     * 默认视图解析器
     */
    private ViewResolver defaultViewResolver;
    private Map<String, ViewResolver> resolvers;

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        logger.debug("======MultiViewResolver==viewName=={}=", viewName);
        // 没有默认使用jsp方式 解析,有的话截取下划线后面的字符串
        int n = viewName.lastIndexOf("_"); // 获取viewName(modelAndView中的名字)看其有没有下划线
        if (n == -1) {
            return defaultViewResolver.resolveViewName(viewName, locale);
        }
        // 这里一般是jsp,ftl与配置文件中的<entry key="ftl">的key匹配
        String suffix = viewName.substring(n + 1);
        // 取下划线前面的部分 那时真正的资源名.比如我们要使用hello.jsp 那viewName就应该是hello_jsp
        viewName = viewName.substring(0, n);
        logger.debug("suffix:{}\tviewName:{}", suffix, viewName);
        // 根据下划线后面的字符串去获取托管的视图解析类对象
        ViewResolver resolver = resolvers.get(suffix);
        View view;
        if (resolver != null) {
            view = resolver.resolveViewName(viewName, locale);
        } else {
            view = defaultViewResolver.resolveViewName(viewName, locale);
        }
        return view;
    }

    public Map<String, ViewResolver> getResolvers() {
        return resolvers;
    }

    public void setResolvers(Map<String, ViewResolver> resolvers) {
        this.resolvers = resolvers;
    }

    public ViewResolver getDefaultViewResolver() {
        return defaultViewResolver;
    }

    public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
        this.defaultViewResolver = defaultViewResolver;
    }
}
