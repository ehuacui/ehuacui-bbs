package org.ehuacui.bbs.exception;

/**
 * 创建服务报告文件异常
 */
public class RBusinessException extends RuntimeException {

    public RBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RBusinessException(String message) {
        super(message);
    }

    public RBusinessException(Throwable cause) {
        super(cause);
    }

}
