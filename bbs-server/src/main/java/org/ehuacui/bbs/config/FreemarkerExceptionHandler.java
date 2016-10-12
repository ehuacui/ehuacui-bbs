package org.ehuacui.bbs.config;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;

/**
 * freemarker页面上的异常控制
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerExceptionHandler.class);

    @Override
    public void handleTemplateException(TemplateException te, Environment environment, Writer writer) throws TemplateException {
        log.warn("[Freemarker Error: " + te.getMessage() + "]");
        throw new BusinessException("freemarker error", te);
    }
}
