package com.vinil.controller;

import com.vinil.model.Loja;
import com.vinil.service.LojaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lojas")
public class AdminLojaController {

    private final LojaService lojaService;

    public AdminLojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lojas", lojaService.listarTodas());
        return "admin/lojas-lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("loja", new Loja());
        return "admin/lojas-form";
    }

    @PostMapping("/novo")
    public String criar(
        @Valid @ModelAttribute Loja loja,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/lojas-form";
        }
        lojaService.criar(loja, loja.getEmail(), loja.getSenha());
        return "redirect:/admin/lojas";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("loja", lojaService.buscarPorId(id));
        return "admin/lojas-form";
    }

    @PostMapping("/{id}/editar")
    public String editar(
        @PathVariable Long id,
        @Valid @ModelAttribute Loja loja,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/lojas-form";
        }
        lojaService.atualizar(id, loja);
        return "redirect:/admin/lojas";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        lojaService.deletar(id);
        return "redirect:/admin/lojas";
    }
}
