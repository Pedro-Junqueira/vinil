package com.vinil.service;

import com.vinil.model.Loja;
import com.vinil.model.enums.Role;
import com.vinil.repository.LojaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService {

    private final LojaRepository lojaRepository;
    private final PasswordEncoder passwordEncoder;

    public LojaService(
        LojaRepository lojaRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.lojaRepository = lojaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Loja> listarTodas() {
        return lojaRepository.findAll();
    }

    public Loja buscarPorId(Long id) {
        return lojaRepository.findById(id).orElseThrow();
    }

    public void criar(Loja loja, String email, String senha) {
        loja.setEmail(email);
        loja.setSenha(passwordEncoder.encode(senha));
        loja.setRole(Role.LOJA);
        lojaRepository.save(loja);
    }

    public void atualizar(Long id, Loja dadosNovos) {
        Loja loja = lojaRepository.findById(id).orElseThrow();
        loja.setNome(dadosNovos.getNome());
        loja.setCnpj(dadosNovos.getCnpj());
        loja.setDescricao(dadosNovos.getDescricao());
        lojaRepository.save(loja);
    }

    public void deletar(Long id) {
        lojaRepository.deleteById(id);
    }
}
