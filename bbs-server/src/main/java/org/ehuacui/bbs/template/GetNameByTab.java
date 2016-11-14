package org.ehuacui.bbs.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.ehuacui.bbs.config.SpringContextHolder;
import org.ehuacui.bbs.model.Section;
import org.ehuacui.bbs.service.SectionService;

import java.util.List;

/**
 * 根据版块标识查询版块名称
 * Created by jianwei.zhou on 2016/9/7.
 */
public class GetNameByTab implements TemplateMethodModelEx {

    private SectionService sectionService = SpringContextHolder.getBean(SectionService.class);

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list == null || list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        String tab = list.get(0).toString();
        Section section = sectionService.findByTab(tab);
        if (section != null) {
            return section.getName();
        }
        return null;
    }
}
