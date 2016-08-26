package org.ehuacui.module;

import org.ehuacui.common.BaseModel;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Notification extends BaseModel<Notification> {

    /**
     * 判断通知是否已读
     */
    public String isRead(Notification notification) {
        if (notification.getBoolean("read")) {
            return "true";
        } else {
            return "false";
        }
    }

}
