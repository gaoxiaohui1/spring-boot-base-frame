package com.base.frame.common.tools.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 发邮件
 */
@Slf4j
@Service
public class MailService {
    @Autowired
    private JavaMailSender sender;
    /**
     * 发信人
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单邮件
     *
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    public void sendSimpleMail(String[] to, String subject, String content) throws Exception {
        log.error("发送简单邮件");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            sender.send(message);
            log.debug("发送简单邮件");
        } catch (Exception e) {
            log.error("发邮件异常");
            throw new Exception("发邮件错误");
        }
    }

    /**
     * 异步发送邮件，
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @throws Exception
     */
    public void sendSimpleMailAsync(String[] to, String subject, String content) throws Exception {
        log.debug("异步发送简单邮件");
//        try {
//            new Thread() {
//                public void run() {
//                    SimpleMailMessage message = new SimpleMailMessage();
//                    message.setFrom(from);
//                    message.setTo(to);
//                    message.setSubject(subject);
//                    message.setText(content);
//                    sender.send(message);
//                    log.debug("已异步发送简单邮件");
//                }
//            }.start();
//        } catch (Exception e) {
//            log.error("异步发邮件异常");
//            throw new Exception("异步发邮件错误");
//        }
    }

    /**
     * 发送html格式的邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String[] to, String subject, String content) throws Exception {
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
            log.debug("html邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
            throw new Exception("发送html邮件时发生异常！");
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String[] to, String subject, String content, String filePath) throws Exception {
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            sender.send(message);
            log.debug("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！", e);
            throw new Exception("发送html邮件时发生异常！");
        }
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param fileName
     * @param file
     */
    public void sendAttachmentsMail(String[] to, String subject, String content, String fileName, DataSource file) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.addAttachment(fileName, file);
            try {
                sender.send(message);
                log.debug("带附件的邮件已经发送。");
            } catch (Exception e) {
                log.error("发送带附件的邮件时发生异常！", e);
                try {
                    String[] newEmailAddress = findValidAddressWhenException(e, to);
                    if (newEmailAddress.length > 0) {
                        helper.setTo(newEmailAddress);
                        sender.send(message);
                    }
                } catch (Exception ex) {

                }
            }
        } catch (Exception e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }
    }


    private String[] findValidAddressWhenException(Exception ex, String[] to) {
        try {
            if (ex instanceof MailSendException) {
                MailSendException exception = (MailSendException) ex;
                SendFailedException sendFailedException = (SendFailedException) exception.getMessageExceptions()[0];
                Address[] validAddresses = sendFailedException.getValidUnsentAddresses();
                if (validAddresses.length > 0) {
                    String[] newEmailAddress = new String[validAddresses.length];
                    for (int i = 0; i < validAddresses.length; i++) {
                        newEmailAddress[i] = validAddresses[i].toString();
                    }
                    return newEmailAddress;
                }
            }
            return new String[0];
        } catch (Exception e) {
            return new String[0];
        }
    }
}
