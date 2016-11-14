package org.ehuacui.bbs.service;

import org.ehuacui.bbs.config.BusinessException;

import java.util.Map;

/**
 * HTML生成服务类根据FreeMarker
 * Created by jianwei.zhou on 2016/11/14.
 */
public interface HtmlGeneratorService {
    /***
     * 根据模板生成内容
     *
     * @param templateFileName 模板名称
     * @param model            模型数据
     * @return 内容信息
     */
    String generateHtmlBody(String templateFileName, Map<String, Object> model) throws BusinessException;

    /**
     * 根据模板生成文件
     *
     * @param directory        生成文件的目录
     * @param fileName         生成文件的名称
     * @param templateFileName 模板名称
     * @param model            模型数据
     * @throws BusinessException 异常信息
     */
    void generateHtmlFile(String directory, String fileName, String templateFileName,
                          Map<String, Object> model) throws BusinessException;
}
