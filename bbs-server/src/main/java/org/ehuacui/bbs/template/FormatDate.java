package org.ehuacui.bbs.template;

import freemarker.template.SimpleDate;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 格式化日期
 * Created by jianwei.zhou on 2016/9/7.
 */
public class FormatDate implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        SimpleDate simpleDate = (SimpleDate) list.get(0);
        Date date = simpleDate.getAsDate();
        PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
        return prettyTime.format(date).replace(" ", "");
    }
}
