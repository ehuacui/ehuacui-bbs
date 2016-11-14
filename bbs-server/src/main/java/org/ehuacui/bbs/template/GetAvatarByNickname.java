package org.ehuacui.bbs.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.ehuacui.bbs.config.SpringContextHolder;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.UserService;

import java.util.List;

/**
 * 根据用户昵称查询用户头像
 * Created by jianwei.zhou on 2016/9/7.
 */
public class GetAvatarByNickname implements TemplateMethodModelEx {

    private UserService userService = SpringContextHolder.getBean(UserService.class);

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String nickname = list.get(0).toString();
        User user = userService.findByNickname(nickname);
        if (user != null) {
            return user.getAvatar();
        }
        return null;
    }
}
