package org.ehuacui.module;

import org.ehuacui.common.BaseModel;
import org.ehuacui.common.Constants.CacheEnum;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Section extends BaseModel<Section> {

    public static final Section me = new Section();

    public List<Section> findAll() {
        return super.find("select * from ehuacui_section");
    }

    public List<Section> findByShowStatus(boolean isshow) {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.sections.name() + isshow);
        if(list == null) {
            list = super.find("select * from ehuacui_section where show_status = ? order by display_index", isshow);
            cache.set(CacheEnum.sections.name() + isshow, list);
        }
        return list;
    }

    public Section findByTab(String tab) {
        Cache cache = Redis.use();
        Section section = cache.get(CacheEnum.section.name() + tab);
        if(section == null) {
            section = findFirst(
                    "select * from ehuacui_section where tab = ?",
                    tab
            );
            cache.set(CacheEnum.section.name() + tab, section);
        }
        return section;
    }

    public String showStatus(Section section) {
        if(section.getBoolean("show_status")) {
            return "true";
        } else {
            return "false";
        }
    }

}
