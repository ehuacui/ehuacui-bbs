package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.config.BusinessException;
import org.ehuacui.bbs.service.EmailSendService;
import org.ehuacui.bbs.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * Email发送服务
 */
@Service
public class EmailSendServiceImpl implements EmailSendService {
    private final static Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    // 发件人地址
    @Value("${mail.from}")
    private String defaultFrom;

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
    @Override
    public void sendTextMail(String from, String to, String[] cc, String mailSubject, String mailBody) throws BusinessException {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            if (StringUtil.isBlank(from)) {
                mail.setFrom(defaultFrom);
            } else {
                mail.setFrom(from);// 发送人名片
            }
            mail.setTo(to);// 收件人邮箱
            if (cc != null && cc.length > 0) {
                mail.setCc(cc);
            }
            mail.setSubject(mailSubject);// 邮件主题
            mail.setSentDate(new Date());// 邮件发送时间
            mail.setText(mailBody);
            javaMailSender.send(mail);
        } catch (MailException e) {
            logger.error("发送邮件时发生异常!", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

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
    @Override
    public void sendHtmlMail(String from, String to, String[] cc, String mailSubject, String mailBody) throws BusinessException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            if (StringUtil.isBlank(from)) {
                mail.setFrom(defaultFrom);
            } else {
                mail.setFrom(from);// 发送人名片
            }
            // 设置收件人名片和地址
            mail.setTo(to);// 发送者
            if (cc != null && cc.length > 0) {
                mail.setCc(cc);
            }
            // 邮件发送时间
            mail.setSentDate(new Date());
            mail.setSubject(mailSubject);
            mail.setText(mailBody, true);
            // 发送
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            logger.error("发送邮件时发生异常!", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

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
    @Override
    public void sendHtmlAndAttachmentFileMail(String from, String to, String[] cc, String mailSubject, String mailBody,
                                              String attachmentDirectory, String attachmentFilename) throws BusinessException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            if (StringUtil.isBlank(from)) {
                mail.setFrom(defaultFrom);
            } else {
                mail.setFrom(from);// 发送人名片
            }
            // 设置收件人名片和地址
            mail.setTo(to);// 发送者
            if (cc != null && cc.length > 0) {
                mail.setCc(cc);
            }
            // 邮件发送时间
            mail.setSentDate(new Date());
            mail.setSubject(mailSubject);
            mail.setText(mailBody, true);
            File attachmentFile = new File(attachmentDirectory, attachmentFilename);
            if (attachmentFile.exists()) {
                mail.addAttachment(attachmentFilename, attachmentFile);
            } else {
                throw new BusinessException("邮件附件内容不存在");
            }
            // 发送
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            logger.error("发送邮件时发生异常!", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

}
