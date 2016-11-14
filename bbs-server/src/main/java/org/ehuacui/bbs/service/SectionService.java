package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.Section;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface SectionService {

    List<Section> findAll();

    List<Section> findByShowStatus(boolean isshow);

    Section findByTab(String tab);

    Section findById(Integer id);

    void save(Section section);

    void update(Section section);

    void deleteById(Integer id);
}
