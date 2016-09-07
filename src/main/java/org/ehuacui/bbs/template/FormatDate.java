package org.ehuacui.bbs.template;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 格式化日期
 * Created by jianwei.zhou on 2016/9/7.
 */
public class FormatDate implements TemplateMethodModel {

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String dateStr = "";
        String strDate = (String) list.get(0);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
            if (date != null) {
                PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
                dateStr = prettyTime.format(date);
                dateStr = dateStr.replace(" ", "");
            }
        } catch (ParseException e) {
            dateStr = "时间未知";
        }
        return dateStr;
    }
}
