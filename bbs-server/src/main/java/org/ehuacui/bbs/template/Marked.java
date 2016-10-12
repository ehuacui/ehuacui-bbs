package org.ehuacui.bbs.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.ehuacui.bbs.utils.MarkdownUtil;
import org.ehuacui.bbs.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.List;

/**
 * 解析markdown文章
 * Created by jianwei.zhou on 2016/9/7.
 */
public class Marked implements TemplateMethodModelEx {

    /**
     * 站点URL
     */
    private String siteDomain;

    public Marked(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String content = list.get(0).toString();
        if (StringUtil.isBlank(content)) return "";

        //@ user url
        String atUserUrl = siteDomain + "/user/";
        //处理@
        List<String> users = StringUtil.fetchUsers(content);
        for (String user : users) {
            content = content.replace("@" + user, "[@" + user + "](" + atUserUrl + user + ")");
        }
        //markdown 转 html 并返回
        return Jsoup.clean(MarkdownUtil.pegDown(content), Whitelist.relaxed().addTags("input").addAttributes("input", "checked", "type"));
    }

}
