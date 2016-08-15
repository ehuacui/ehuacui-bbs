package org.ehuacui.service.impl;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.common.BaseModel;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.module.Section;
import org.ehuacui.service.ISection;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class SectionService implements ISection {

    private Section me = new Section();

    @Override
    public List<Section> findAll() {
        return me.find("select * from ehuacui_section");
    }

    @Override
    public List<Section> findByShowStatus(boolean isshow) {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.sections.name() + isshow);
        if(list == null) {
            list = me.find("select * from ehuacui_section where show_status = ? order by display_index", isshow);
            cache.set(CacheEnum.sections.name() + isshow, list);
        }
        return list;
    }

    @Override
    public Section findById(Integer id) {
        return me.findById(id);
    }

    @Override
    public Section findByTab(String tab) {
        Cache cache = Redis.use();
        Section section = cache.get(CacheEnum.section.name() + tab);
        if(section == null) {
            section = me.findFirst(
                    "select * from ehuacui_section where tab = ?",
                    tab
            );
            cache.set(CacheEnum.section.name() + tab, section);
        }
        return section;
    }

}
