package org.ehuacui.bbs.utils;

/**
 * ContentType
 * <br>
 * TOMCAT-HOME/conf/web.xml
 * <br>
 * http://tool.oschina.net/commons
 */
public enum ContentType {

    TEXT("text/plain"),
    HTML("text/html"),
    XML("text/xml"),
    JSON("application/json"),
    JAVASCRIPT("application/javascript");

    private final String value;

    private ContentType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public String toString() {
        return value;
    }
}
