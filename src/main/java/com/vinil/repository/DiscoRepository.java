package com.vinil.repository;

import com.vinil.model.Disco;
import com.vinil.model.Loja;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiscoRepository extends JpaRepository<Disco, Long> {
    List<Disco> findByVendido(Boolean vendido);
    List<Disco> findByArtistaContainingIgnoreCaseAndVendido(String artista, Boolean vendido);
    List<Disco> findByGeneroMusicalContainingIgnoreCaseAndVendido(String genero, Boolean vendido);
    List<Disco> findByArtistaContainingIgnoreCaseAndGeneroMusicalContainingIgnoreCaseAndVendido(String artista, String genero, Boolean vendido);
    List<Disco> findByLoja(Loja loja);
}
