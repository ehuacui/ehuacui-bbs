package org.ehuacui.module;

import org.ehuacui.common.BaseModel;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Section extends BaseModel<Section> {

    public String showStatus(Section section) {
        if (section.getBoolean("show_status")) {
            return "true";
        } else {
            return "false";
        }
    }

}
