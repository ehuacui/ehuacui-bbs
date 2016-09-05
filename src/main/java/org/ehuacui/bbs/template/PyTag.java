package org.ehuacui.bbs.template;

import freemarker.template.SimpleHash;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PyTag extends SimpleHash {

    public PyTag() {
        put("hasPermission", new PermissionDirective());
        put("scores", new ScoresDirective());
    }
}
