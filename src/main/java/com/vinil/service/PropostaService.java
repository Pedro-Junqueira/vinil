package com.vinil.service;

import com.vinil.model.Cliente;
import com.vinil.model.Disco;
import com.vinil.model.Proposta;
import com.vinil.model.enums.StatusProposta;
import com.vinil.repository.ClienteRepository;
import com.vinil.repository.DiscoRepository;
import com.vinil.repository.PropostaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final ClienteRepository clienteRepository;
    private final DiscoRepository discoRepository;
    private final EmailService emailService;

    public PropostaService(
        PropostaRepository propostaRepository,
        ClienteRepository clienteRepository,
        DiscoRepository discoRepository,
        EmailService emailService
    ) {
        this.propostaRepository = propostaRepository;
        this.clienteRepository = clienteRepository;
        this.discoRepository = discoRepository;
        this.emailService = emailService;
    }

    public void criar(Long discoId, String valor, String condicoesPagamento, String emailCliente) {
        Cliente cliente = clienteRepository.findByUsuarioEmail(emailCliente);
        Disco disco = discoRepository.findById(discoId).orElseThrow();

        Proposta existente = propostaRepository.findByClienteAndDiscoAndStatus(
            cliente, disco, StatusProposta.ABERTO
        );

        if (existente != null) {
            throw new IllegalStateException("Você já tem uma proposta em aberto para este disco.");
        }

        Proposta proposta = new Proposta();
        proposta.setCliente(cliente);
        proposta.setDisco(disco);
        proposta.setValor(new java.math.BigDecimal(valor));
        proposta.setCondicoesPagamento(condicoesPagamento);
        proposta.setDataProposta(LocalDate.now());
        proposta.setStatus(StatusProposta.ABERTO);

        propostaRepository.save(proposta);
    }

    public List<Proposta> listarPorCliente(String emailCliente) {
        return propostaRepository.findByClienteUsuarioEmail(emailCliente);
    }

    public List<Proposta> listarPorLoja(String emailLoja) {
        return propostaRepository.findByDiscoLojaUsuarioEmail(emailLoja);
    }

    public void aceitar(Long propostaId, String horarioReuniao, String linkVideoconferencia) {
        Proposta proposta = propostaRepository.findById(propostaId).orElseThrow();

        proposta.setStatus(StatusProposta.ACEITO);
        proposta.getDisco().setVendido(true);

        propostaRepository.save(proposta);
        discoRepository.save(proposta.getDisco());

        emailService.notificarPropostaAceita(
            proposta.getCliente().getUsuario().getEmail(),
            proposta.getDisco().getTitulo(),
            horarioReuniao,
            linkVideoconferencia
        );
    }

    public void rejeitar(Long propostaId, String contraproposta) {
        Proposta proposta = propostaRepository.findById(propostaId).orElseThrow();

        proposta.setStatus(StatusProposta.RECUSADO);
        propostaRepository.save(proposta);

        emailService.notificarPropostaRejeitada(
            proposta.getCliente().getUsuario().getEmail(),
            proposta.getDisco().getTitulo(),
            contraproposta
        );
    }
}