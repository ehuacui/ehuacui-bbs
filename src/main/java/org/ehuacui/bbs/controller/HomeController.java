package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.interceptor.BasicInterceptor;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HomeController
 * Created by Administrator on 2016/9/13.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @ResponseBody
    @BeforeAdviceController(BasicInterceptor.class)
    @RequestMapping("/index")
    public Map<String, Object> home() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("code", 200);
        return data;
    }

}
