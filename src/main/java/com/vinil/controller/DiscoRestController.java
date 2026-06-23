package com.vinil.controller;

import com.vinil.model.Disco;
import com.vinil.service.DiscoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discos")
public class DiscoRestController {

    private final DiscoService discoService;

    public DiscoRestController(DiscoService discoService) {
        this.discoService = discoService;
    }

    @PostMapping("/lojas/{id}")
    public ResponseEntity<Disco> criar(@PathVariable Long id, @RequestBody Disco disco) {
        Disco salvo = discoService.cadastrarPorLojaId(disco, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/lojas/{id}")
    public List<Disco> listarPorLoja(@PathVariable Long id) {
        return discoService.listarPorLojaId(id);
    }

    @GetMapping("/artistas/{nome}")
    public List<Disco> listarPorArtista(@PathVariable String nome) {
        return discoService.listarPorArtista(nome);
    }
}
