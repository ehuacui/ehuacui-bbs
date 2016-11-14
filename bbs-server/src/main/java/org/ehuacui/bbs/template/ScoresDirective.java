package org.ehuacui.bbs.template;

import freemarker.core.Environment;
import freemarker.template.*;
import org.ehuacui.bbs.config.SpringContextHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class ScoresDirective implements TemplateDirectiveModel {

    private UserService userService = SpringContextHolder.getBean(UserService.class);

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody)
            throws TemplateException, IOException {
        List<User> scores = new ArrayList<User>();
        if (map.containsKey("limit") && map.get("limit") != null) {
            scores = userService.scores(Integer.parseInt(map.get("limit").toString()));
        }
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
        environment.setVariable("list", builder.build().wrap(scores));
        templateDirectiveBody.render(environment.getOut());
    }

}
