package org.ehuacui.bbs.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.ehuacui.bbs.config.BusinessException;
import org.ehuacui.bbs.service.HtmlGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * HTML生成服务类根据FreeMarker
 */
@Service
public class HtmlGeneratorServiceImpl implements HtmlGeneratorService {

    private final static Logger logger = LoggerFactory.getLogger(HtmlGeneratorServiceImpl.class);

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /***
     * 根据模板生成内容
     *
     * @param templateFileName 模板名称
     * @param model            模型数据
     * @return 内容信息
     */
    @Override
    public String generateHtmlBody(String templateFileName, Map<String, Object> model) throws BusinessException {
        String htmlText;
        try {
            //通过指定模板名获取FreeMarker模板实例
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateFileName);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
        } catch (IOException | TemplateException e) {
            logger.error("模板解析错误---->" + templateFileName);
            throw new BusinessException("模板解析错误---->" + templateFileName, e);
        }
        return htmlText;
    }

    /**
     * 根据模板生成文件
     *
     * @param directory        生成文件的目录
     * @param fileName         生成文件的名称
     * @param templateFileName 模板名称
     * @param model            模型数据
     * @throws BusinessException 异常信息
     */
    @Override
    public void generateHtmlFile(String directory, String fileName, String templateFileName,
                                 Map<String, Object> model) throws BusinessException {
        try {
            String tplHtmlString = generateHtmlBody(templateFileName, model);
            File tmpFile = new File(directory, fileName);
            FileUtils.writeStringToFile(tmpFile, tplHtmlString, "UTF-8");
        } catch (IOException | BusinessException e) {
            logger.error("根据模板生成文件错误---->" + fileName);
            throw new BusinessException("根据模板生成文件错误---->" + fileName, e);
        }
    }

}
