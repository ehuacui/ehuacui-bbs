package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Section;

import java.util.List;

public interface SectionMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

    List<Section> selectAll();

    List<Section> selectByShowStatus(@Param("showStatus") boolean showStatus);

    Section selectByShowTab(@Param("tab") String tab);
}