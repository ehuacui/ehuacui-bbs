package org.ehuacui.service;

import org.ehuacui.module.Section;
import org.ehuacui.service.impl.SectionService;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface ISectionService {

    List<Section> findAll();

    List<Section> findByShowStatus(boolean isshow);

    Section findByTab(String tab);

    Section findById(Integer id);

}
