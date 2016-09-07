package org.ehuacui.bbs.template;

import com.jfinal.kit.PropKit;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.ehuacui.bbs.utils.MarkdownUtil;
import org.ehuacui.bbs.utils.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.List;

/**
 * 解析markdown文章
 * Created by jianwei.zhou on 2016/9/7.
 */
public class Marked implements TemplateMethodModel {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String content = (String) list.get(0);
        if (StrUtil.isBlank(content)) return "";
        //处理@
        List<String> users = StrUtil.fetchUsers(content);
        for (String user : users) {
            content = content.replace("@" + user, "[@" + user + "](" + PropKit.get("site.domain") + "/user/" + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }
}
