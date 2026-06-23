package com.vinil.controller;

import com.vinil.model.Loja;
import com.vinil.service.LojaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
public class LojaRestController {
    private final LojaService lojaService;

    public LojaRestController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @PostMapping
    public ResponseEntity<Loja> criar(@RequestBody Loja loja) {
        lojaService.criar(loja, loja.getEmail(), loja.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(loja);
    }

    @GetMapping
    public List<Loja> listar() {
        return lojaService.listarTodas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Loja> buscarPorId(@PathVariable Long id) {
        Loja loja = lojaService.buscarPorId(id);
        return ResponseEntity.ok(loja);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Loja> atualizar(@PathVariable Long id, @RequestBody Loja loja) {
        lojaService.atualizar(id, loja);
        return ResponseEntity.ok(lojaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id){
            lojaService.deletar(id);
            return ResponseEntity.noContent().build();
        }
    


 }