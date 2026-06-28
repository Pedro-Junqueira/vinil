package com.vinil.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notificarPropostaAceita(String emailCliente, String tituloDisco, String horarioReuniao, String linkVideoconferencia) {
        String corpo = "Sua proposta para o disco '" + tituloDisco + "' foi ACEITA!\n\n"
                + "Horário da reunião: " + horarioReuniao + "\n"
                + "Link da videoconferência: " + linkVideoconferencia;

        enviar(emailCliente, "Proposta aceita - " + tituloDisco, corpo);
    }

    public void notificarPropostaRejeitada(String emailCliente, String tituloDisco, String contraproposta) {
        String corpo = "Sua proposta para o disco '" + tituloDisco + "' não foi aceita.";

        if (contraproposta != null && !contraproposta.isEmpty()) {
            corpo += "\n\nContraproposta da loja: " + contraproposta;
        }

        enviar(emailCliente, "Proposta não aceita - " + tituloDisco, corpo);
    }

    private void enviar(String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        mailSender.send(mensagem);

        System.out.println(">> E-mail enviado para: " + destinatario);
    }
}
