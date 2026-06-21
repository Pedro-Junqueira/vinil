package com.vinil.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void notificarPropostaAceita(String emailCliente, String tituloDisco, String horarioReuniao, String linkVideoconferencia) {
        System.out.println("=== E-MAIL ENVIADO ===");
        System.out.println("Para: " + emailCliente);
        System.out.println("Assunto: Proposta aceita - " + tituloDisco);
        System.out.println("Sua proposta para o disco '" + tituloDisco + "' foi ACEITA!");
        System.out.println("Horário da reunião: " + horarioReuniao);
        System.out.println("Link da videoconferência: " + linkVideoconferencia);
        System.out.println("======================");
    }

    public void notificarPropostaRejeitada(String emailCliente, String tituloDisco, String contraproposta) {
        System.out.println("=== E-MAIL ENVIADO ===");
        System.out.println("Para: " + emailCliente);
        System.out.println("Assunto: Proposta não aceita - " + tituloDisco);
        System.out.println("Sua proposta para o disco '" + tituloDisco + "' não foi aceita.");
        if (contraproposta != null && !contraproposta.isEmpty()) {
            System.out.println("Contraproposta da loja: " + contraproposta);
        }
        System.out.println("======================");
    }
}