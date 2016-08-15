package org.ehuacui.service.impl;

import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Section;
import org.ehuacui.service.ISectionService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class SectionService implements ISectionService {

    @Override
    public List<Section> findAll() {
        return DaoHolder.sectionDao.findAll();
    }

    @Override
    public List<Section> findByShowStatus(boolean isshow) {
        return DaoHolder.sectionDao.findByShowStatus(isshow);
    }

    @Override
    public Section findById(Integer id) {
        return DaoHolder.sectionDao.findById(id);
    }

    @Override
    public Section findByTab(String tab) {
        return DaoHolder.sectionDao.findByTab(tab);
    }

}
