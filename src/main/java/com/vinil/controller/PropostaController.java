package com.vinil.controller;

import com.vinil.repository.DiscoRepository;
import com.vinil.service.PropostaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PropostaController {

    private final PropostaService propostaService;
    private final DiscoRepository discoRepository;

    public PropostaController(PropostaService propostaService, DiscoRepository discoRepository) {
        this.propostaService = propostaService;
        this.discoRepository = discoRepository;
    }

    @GetMapping("/cliente/propostas/novo/{discoId}")
    public String formNovaProposta(@PathVariable Long discoId, Model model) {
        model.addAttribute("disco", discoRepository.findById(discoId).orElseThrow());
        return "propostas/form";
    }

    @PostMapping("/cliente/propostas/novo")
    public String criarProposta(
        @RequestParam Long discoId,
        @RequestParam String valor,
        @RequestParam String condicoesPagamento,
        Authentication authentication,
        Model model
    ) {
        try {
            propostaService.criar(discoId, valor, condicoesPagamento, authentication.getName());
            return "redirect:/cliente/propostas";
        } catch (IllegalStateException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("disco", discoRepository.findById(discoId).orElseThrow());
            return "propostas/form";
        }
    }

    @GetMapping("/cliente/propostas")
    public String listarPropostas(Model model, Authentication authentication) {
        model.addAttribute("propostas", propostaService.listarPorCliente(authentication.getName()));
        return "propostas/lista";
    }

    @GetMapping("/loja/propostas")
    public String listarPropostasDaLoja(Model model, Authentication authentication) {
        model.addAttribute("propostas", propostaService.listarPorLoja(authentication.getName()));
        return "propostas/lista-loja";
    }

    @PostMapping("/loja/propostas/{id}/aceitar")
    public String aceitarProposta(
        @PathVariable Long id,
        @RequestParam String horarioReuniao,
        @RequestParam String linkVideoconferencia
    ) {
        propostaService.aceitar(id, horarioReuniao, linkVideoconferencia);
        return "redirect:/loja/propostas";
    }

    @PostMapping("/loja/propostas/{id}/rejeitar")
    public String rejeitarProposta(
        @PathVariable Long id,
        @RequestParam(required = false) String contraproposta
    ) {
        propostaService.rejeitar(id, contraproposta);
        return "redirect:/loja/propostas";
    }
}
