package com.vinil.controller;

import com.vinil.model.Proposta;
import com.vinil.service.PropostaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propostas")
public class PropostaRestController {
    private final PropostaService propostaService;

    public PropostaRestController(PropostaService propostaService){
        this.propostaService = propostaService;
    }

    @GetMapping("/discos/{id}")
    public List<Proposta> listarPorDiscoId(@PathVariable Long discoId){
        return propostaService.listarPorDiscoId(discoId);
    }

    @GetMapping("/clientes/{id}")
    public List<Proposta> listarPorClienteId(@PathVariable Long clienteId){
        return propostaService.listarPorClienteId(clienteId);
    }
}