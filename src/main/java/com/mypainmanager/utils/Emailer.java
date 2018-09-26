/*
 * Copyright 2018 Harrison Ellerm.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mypainmanager.utils;

import com.mypainmanager.domain.UserInfo;
import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.MimeUtility;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

/**
 *
 * @author Harrison Ellerm
 */
public class Emailer {

    static final Logger LOG = Logger.getLogger(Emailer.class);

    public static void sendExceptionToSupportAndLog(Exception ex, Logger log, Class clazz) {
        LOG.error("MPM-Report-Gen-Exe: Exception [" + clazz + "]", ex);
        HtmlContent msg = new HtmlContent(clazz);
        msg.addException(ex, LOG);
        sendMPMReportErrorToRecipient(msg);
    }
    
    public static void sendApplicationExceptionToSupportAndLog(String ex, String clazz, Logger log) {
         LOG.error("MPM-App-Exe: Exception raised [" + clazz + "]");
         LOG.error("MPM-App-Exe: Exception raised [" + ex + "]");
         HtmlContent msg = new HtmlContent(clazz);
         msg.addException(ex, LOG);
         sendMPMAppExceptionToStaff(msg);
    }
    
    private static void sendMPMAppExceptionToStaff(HtmlContent message) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(Settings.getInstance().getExceptionEmail(),
                    Settings.getInstance().getPassword()));
            setupGenericEmailProperties(email);
            email.addTo(Settings.getInstance().getStaff());
            email.setFrom(Settings.getInstance().getExceptionEmail());
            email.setSubject("MPM Application Exception: Error");
            email.setHtmlMsg(message.getHtml());
            email.send();
        } catch (EmailException ex) {
            LOG.error(null, ex);
        }
    }

    private static void sendMPMReportErrorToRecipient(HtmlContent message) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(Settings.getInstance().getExceptionEmail(),
                    Settings.getInstance().getPassword()));
            setupGenericEmailProperties(email);
            email.addTo(Settings.getInstance().getStaff());
            email.setFrom(Settings.getInstance().getExceptionEmail());
            email.setSubject("MPM Report Generator: Error");
            email.setHtmlMsg(message.getHtml());
            email.send();
        } catch (EmailException ex) {
            LOG.error(null, ex);
        }
    }

    public static void sendPDFReport(UserInfo user, File file) {
        LOG.info("Send notification email: " + user.getEmail());
        try {
            HtmlContent message = new HtmlContent("Report Generated");
            message.addLine("Hello, ", LOG);
            message.addLine("", LOG);
            message.addLine("This is an automated message letting you know that your report has been generated.", LOG);
            message.addLine("", LOG);
            message.addLine("If you have any questions please contact harryellerm@gmail.com", LOG);
            message.addLine("", LOG);
            message.addLine("Regards,", LOG);
            message.addLine("", LOG);
            message.addLine("The MyPainManager Team", LOG);
            LOG.info("Body: " + message.getHtml());

            HtmlEmail email = new HtmlEmail();
            email.setAuthenticator(new DefaultAuthenticator(Settings.getInstance().getReportEmail(),
                    Settings.getInstance().getPassword()));
            setupGenericEmailProperties(email);
            email.addTo(user.getEmail());
            email.setFrom(Settings.getInstance().getReportEmail());
            email.setSubject("MPM Report Generator: Your report");
            email.setHtmlMsg(message.getHtmlWithoutHeader());
            EmailAttachment at = new EmailAttachment();
            at.setDisposition(EmailAttachment.ATTACHMENT);
            at.setPath(file.getAbsolutePath());
            at.setName(MimeUtility.encodeText(file.getName()));
            at.setDescription("PDF Report");
            email.attach(at);
            email.send();
        } catch (EmailException | UnsupportedEncodingException ex) {
            LOG.error(null, ex);
        }
    }

    private static void setupGenericEmailProperties(HtmlEmail email) {
        try {
            email.setHostName(Settings.getInstance().getSmtpHost());
            email.getMailSession().getProperties().put("mail.smtps.auth", "true");
            email.getMailSession().getProperties().put("mail.debug", "true");
            email.getMailSession().getProperties().put("mail.smtps.port", Settings.getInstance().getSmtpPort());
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", Settings.getInstance().getSmtpPort());
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
        } catch (EmailException ex) {
             LOG.error(null, ex);
        }
    }
}
