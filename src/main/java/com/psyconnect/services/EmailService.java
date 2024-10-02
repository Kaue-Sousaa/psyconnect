package com.psyconnect.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	
	@Value("spring.mail.username")
	private String remetente;
	
	public void enviarEmailTexto(String destinatario, String assunto, String conteudoHtml) {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	        helper.setTo(destinatario);
	        helper.setSubject(assunto);
	        helper.setText(conteudoHtml, true);
	        javaMailSender.send(mimeMessage);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
