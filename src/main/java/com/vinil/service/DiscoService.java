package com.vinil.service;

import com.vinil.model.Disco;
import com.vinil.model.Loja;
import com.vinil.repository.DiscoRepository;
import com.vinil.repository.LojaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiscoService {

    private final DiscoRepository discoRepository;
    private final LojaRepository lojaRepository;

    public DiscoService(DiscoRepository discoRepository, LojaRepository lojaRepository) {
        this.discoRepository = discoRepository;
        this.lojaRepository = lojaRepository;
    }

    public List<Disco> listarDisponiveis(String artista, String genero) {
        if (artista != null && !artista.isEmpty() && genero != null && !genero.isEmpty()) {
            return discoRepository.findByArtistaContainingIgnoreCaseAndGeneroMusicalContainingIgnoreCaseAndVendido(artista, genero, false);
        } else if (artista != null && !artista.isEmpty()) {
            return discoRepository.findByArtistaContainingIgnoreCaseAndVendido(artista, false);
        } else if (genero != null && !genero.isEmpty()) {
            return discoRepository.findByGeneroMusicalContainingIgnoreCaseAndVendido(genero, false);
        } else {
            return discoRepository.findByVendido(false);
        }
    }

    public void cadastrar(Disco disco, String emailLoja) {
        Loja loja = lojaRepository.findByUsuarioEmail(emailLoja);
        disco.setLoja(loja);
        disco.setVendido(false);
        discoRepository.save(disco);
    }

    public List<Disco> listarPorLoja(String emailLoja) {
        Loja loja = lojaRepository.findByUsuarioEmail(emailLoja);
        return discoRepository.findByLoja(loja);
}
}