package com.vinil.controller;

import com.vinil.model.Disco;
import com.vinil.model.enums.EstadoConservacao;
import com.vinil.service.DiscoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiscoController {

    private final DiscoService discoService;

    public DiscoController(DiscoService discoService) {
        this.discoService = discoService;
    }

    @GetMapping("/discos")
    public String listarDiscos(
        @RequestParam(required = false) String artista,
        @RequestParam(required = false) String genero,
        Model model
    ) {
        model.addAttribute("discos", discoService.listarDisponiveis(artista, genero));
        model.addAttribute("artista", artista);
        model.addAttribute("genero", genero);
        return "discos/lista";
    }

    @GetMapping("/loja/discos/novo")
    public String formNovoDisco(Model model) {
        model.addAttribute("disco", new Disco());
        model.addAttribute("estados", EstadoConservacao.values());
        return "discos/form";
    }

    @PostMapping("/loja/discos/novo")
    public String cadastrarDisco(
        @Valid @ModelAttribute Disco disco,
        BindingResult bindingResult,
        Authentication authentication,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("estados", EstadoConservacao.values());
            return "discos/form";
        }
        discoService.cadastrar(disco, authentication.getName());
        return "redirect:/loja/discos";
    }

    @GetMapping("/loja/discos")
    public String listarDiscosDaLoja(Model model, Authentication authentication) {
        model.addAttribute("discos", discoService.listarPorLoja(authentication.getName()));
        return "discos/lista-loja";
    }
}
