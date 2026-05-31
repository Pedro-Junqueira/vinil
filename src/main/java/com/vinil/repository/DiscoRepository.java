package com.vinil.repository;

import com.vinil.model.Disco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscoRepository extends JpaRepository<Disco, Long> {
}