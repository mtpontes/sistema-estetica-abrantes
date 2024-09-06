package br.com.karol.sistema.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.karol.sistema.api.factory.EmailFactory;
import br.com.karol.sistema.domain.valueobjects.Email;

@Service
public class EmailSendService {
    
    private String remetente;
    private final JavaMailSender javaMailSender;
    private final EmailFactory emailMapper;
    private final TokenService tokenService;

    public EmailSendService(
        @Value("${spring.mail.username}") String remetente,
        JavaMailSender javaMailSender,
        EmailFactory emailMapper,
        TokenService tokenService
    ) {
        this.remetente = remetente;
        this.javaMailSender = javaMailSender;
        this.emailMapper = emailMapper;
        this.tokenService = tokenService;
    }


    public void sendEmailVerification(String input) {
        Email email = emailMapper.createEmail(input); // valida o email
        String emailToken = tokenService.generateToken(email.getValue());

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom(this.remetente);
        emailMessage.setSubject("Verificação de email");
        emailMessage.setText(emailToken);
        emailMessage.setTo(email.getValue());
        
        javaMailSender.send(emailMessage);
    }

    @Async
    public void sendEmailAsync(SimpleMailMessage mailMessage) {
        javaMailSender.send(mailMessage);
    }
}