package org.ehuacui.common;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Model;
import org.ehuacui.module.Section;
import org.ehuacui.module.User;
import org.ehuacui.service.ISection;
import org.ehuacui.service.IUser;
import org.ehuacui.utils.MarkdownUtil;
import org.ehuacui.utils.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class BaseModel<T extends Model> extends Model<T> {

    static {
        PropKit.use("config.properties");
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public String formatDate(Date date) {
        String dateStr = "";
        if (date != null) {
            PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
            dateStr = prettyTime.format(date);
        }
        return dateStr.replace(" ", "");
    }

    /**
     * 根据版块标识查询版块名称
     *
     * @param tab
     * @return
     */
    public String getNameByTab(String tab) {
        Section section = ServiceHolder.sectionService.findByTab(tab);
        if (section != null) {
            return section.getStr("name");
        }
        return null;
    }

    /**
     * 根据用户昵称查询用户头像
     *
     * @param nickname
     * @return
     */
    public String getAvatarByNickname(String nickname) throws UnsupportedEncodingException {
        User user = ServiceHolder.userService.findByNickname(nickname);
        if (user != null) {
            return user.getStr("avatar");
        }
        return null;
    }

    /**
     * 解析markdown文章
     *
     * @param content
     * @return
     */
    public String marked(String content) {
        if (StrUtil.isBlank(content)) return "";
        //处理@
        List<String> users = StrUtil.fetchUsers(content);
        for (String user : users) {
            content = content.replace("@" + user, "[@" + user + "](" + PropKit.get("site.domain") + "/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

    /**
     * 解析markdown文章(不解析@)
     *
     * @param content
     * @return
     */
    public String markedNotAt(String content) {
        if (StrUtil.isBlank(content)) return "";
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

}
