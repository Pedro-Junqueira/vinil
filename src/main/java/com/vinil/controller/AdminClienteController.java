package com.vinil.controller;

import com.vinil.model.Cliente;
import com.vinil.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class AdminClienteController {

    private final ClienteService clienteService;

    public AdminClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "admin/clientes-lista";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/clientes-form";
    }

    @PostMapping("/novo")
    public String criar(
        @Valid @ModelAttribute Cliente cliente,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/clientes-form";
        }
        try {
            clienteService.criar(cliente, cliente.getEmail(), cliente.getSenha());
            return "redirect:/admin/clientes";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erroDuplicado", true);
            return "admin/clientes-form";
        }
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "admin/clientes-form";
    }

    @PostMapping("/{id}/editar")
    public String editar(
        @PathVariable Long id,
        @Valid @ModelAttribute Cliente cliente,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/clientes-form";
        }
        try {
            clienteService.atualizar(id, cliente);
            return "redirect:/admin/clientes";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erroDuplicado", true);
            return "admin/clientes-form";
        }
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/admin/clientes";
    }
}
