package am.hovall.common.service.impl;

import am.hovall.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSender mailSender;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }

    @Async
    public void sendHtmlEmail(String to, String subject, User user,
                              String link, String templateName) throws MessagingException {
        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("name", user.getName());
        ctx.setVariable("url", link);
        final String htmlContent = templateEngine.process(templateName, ctx);
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setTo(to);
        message.setText(htmlContent, true);
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setTo("test@host.com");
//        helper.setText("Check out this image!");
//        FileSystemResource file = new FileSystemResource(new File("E:\\eComerceSocecoTeamWork\\common\\src\\main\\resources\\templates\\email\\images\\image-1.png"));
//        helper.addAttachment("CoolImage.jpg", file);
        this.javaMailSender.send(mimeMessage);
    }


}