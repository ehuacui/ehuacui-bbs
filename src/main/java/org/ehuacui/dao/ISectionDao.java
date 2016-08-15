package org.ehuacui.dao;

import org.ehuacui.module.Section;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface ISectionDao {

    List<Section> findAll();

    List<Section> findByShowStatus(boolean isshow);

    Section findByTab(String tab);

    Section findById(Integer id);

}
