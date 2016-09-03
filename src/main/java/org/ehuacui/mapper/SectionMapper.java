package org.ehuacui.mapper;

import org.ehuacui.model.Section;

import java.util.List;

public interface SectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

    List<Section> selectAll();

    List<Section> selectByShowStatus(boolean showStatus);

    Section selectByShowTab(String tab);
}