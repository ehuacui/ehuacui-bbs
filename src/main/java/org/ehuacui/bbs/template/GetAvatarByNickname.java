package org.ehuacui.bbs.template;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.model.User;

import java.util.List;

/**
 * 根据用户昵称查询用户头像
 * Created by jianwei.zhou on 2016/9/7.
 */
public class GetAvatarByNickname implements TemplateMethodModel {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String nickname = (String) list.get(0);
        User user = ServiceHolder.userService.findByNickname(nickname);
        if (user != null) {
            return user.getAvatar();
        }
        return null;
    }
}
