package org.ehuacui.exception;

/**
 * 创建服务报告文件异常
 */
public class ReportInternalException extends RuntimeException {

    public ReportInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportInternalException(String message) {
        super(message);
    }

    public ReportInternalException(Throwable cause) {
        super(cause);
    }

}
