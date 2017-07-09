package com.shop.webapp.service.mail;

import com.shop.webapp.config.MailConfig;
import com.shop.webapp.config.MessageResource;
import com.shop.webapp.User;

import java.io.StringWriter;
import java.util.function.Function;
import org.slf4j.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Service for sending e-mails.
 * <p>
 * @Asynchronous annotation is used to send e-mails asynchronously.
 * </p>
 */
@Stateless
public class MailService {

    @Inject
    private Logger log;

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";

    @Inject
    private VelocityEngine engine;
    @Inject
    private MessageResource messageResource;
    @Inject
    private MailConfig mailConfig;

    @Asynchronous
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        try {
            log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", isMultipart, isHtml, to, subject, content);
            // Prepare message using a apache commons-email
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailConfig.getHost());
            email.setStartTLSEnabled(true);
            email.setSmtpPort(mailConfig.getPort());
            email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(), mailConfig.getPassword()));
            email.setFrom(mailConfig.getFrom());
            email.setSubject(subject);
            email.setHtmlMsg(content);
            email.addTo(to);
            email.send();
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Asynchronous
    public void sendActivationEmail(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        String subject = messageResource.getActivationTitle();
        String content = getContent(user, baseUrl, "activationEmail");
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Asynchronous
    public void sendCreationEmail(User user, String baseUrl) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        String subject = messageResource.getCreationTitle();
        String content = getContent(user, baseUrl, "creationEmail");
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Asynchronous
    public void sendPasswordResetMail(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        String subject = messageResource.getResetTitle();
        String content = getContent(user, baseUrl, "passwordResetEmail");
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    private String getContent(User user, String baseUrl, String template) {
        Template t = engine.getTemplate(String.format("mails/%s.html", template));
        VelocityContext context = new VelocityContext();
        context.put(USER, user);
        context.put(BASE_URL, baseUrl);
        context.put("props", (Function<String, String>) ConfigResolver::getPropertyValue);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString();
    }

}
