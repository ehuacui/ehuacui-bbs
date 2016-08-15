package org.ehuacui.module;

import org.ehuacui.common.BaseModel;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Topic extends BaseModel<Topic> {

    /**
     * 转换置顶状态
     *
     * @param topic
     * @return
     */
    public String isTop(Topic topic) {
        if (topic.getBoolean("top")) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 转换精华状态
     *
     * @param topic
     * @return
     */
    public String isGood(Topic topic) {
        if (topic.getBoolean("good")) {
            return "true";
        } else {
            return "false";
        }
    }

}
