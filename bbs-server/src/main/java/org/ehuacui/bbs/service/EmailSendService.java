package org.ehuacui.bbs.service;

import org.ehuacui.bbs.config.BusinessException;

/**
 * Email发送服务
 * Created by jianwei.zhou on 2016/11/14.
 */
public interface EmailSendService {
    /**
     * 发送普通文本邮件
     *
     * @param from        发件人
     * @param to          收件人
     * @param cc          抄送人列表
     * @param mailSubject 邮件主题
     * @param mailBody    邮件内容
     * @throws BusinessException
     */
    void sendTextMail(String from, String to, String[] cc, String mailSubject, String mailBody) throws BusinessException;

    /**
     * 发送HTML邮件
     *
     * @param from        发件人
     * @param to          收件人
     * @param cc          抄送人列表
     * @param mailSubject 邮件主题
     * @param mailBody    邮件内容
     * @throws BusinessException
     */
    void sendHtmlMail(String from, String to, String[] cc, String mailSubject, String mailBody) throws BusinessException;

    /**
     * 发送附带文件的HTML邮件
     *
     * @param from                发件人
     * @param to                  收件人
     * @param cc                  抄送人列表
     * @param mailSubject         邮件主题
     * @param mailBody            邮件内容
     * @param attachmentDirectory 附件文件目录
     * @param attachmentFilename  附件文件名
     * @throws BusinessException
     */
    void sendHtmlAndAttachmentFileMail(String from, String to, String[] cc, String mailSubject, String mailBody,
                                       String attachmentDirectory, String attachmentFilename) throws BusinessException;
}
