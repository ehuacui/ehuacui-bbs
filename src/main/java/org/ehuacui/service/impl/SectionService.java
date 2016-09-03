package org.ehuacui.service.impl;

import org.ehuacui.mapper.SectionMapper;
import org.ehuacui.model.Section;
import org.ehuacui.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class SectionService implements ISectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Override
    public List<Section> findAll() {
        return sectionMapper.selectAll();
    }

    @Override
    public List<Section> findByShowStatus(boolean isshow) {
        return sectionMapper.selectByShowStatus(isshow);
    }

    @Override
    public Section findById(Integer id) {
        return sectionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Section section) {
        sectionMapper.insert(section);
    }

    @Override
    public void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }

    @Override
    public void deleteById(Integer id) {
        sectionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Section findByTab(String tab) {
        return sectionMapper.selectByShowTab(tab);
    }

}
