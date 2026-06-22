package com.vinil.repository;

import com.vinil.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Long> {
    Loja findByEmail(String email);
}
