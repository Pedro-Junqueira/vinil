package com.vinil.repository;

import com.vinil.model.Cliente;
import com.vinil.model.Disco;
import com.vinil.model.Proposta;
import com.vinil.model.enums.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Proposta findByClienteAndDiscoAndStatus(Cliente cliente, Disco disco, StatusProposta status);
    List<Proposta> findByClienteUsuarioEmail(String email);
    List<Proposta> findByDiscoLojaUsuarioEmail(String email);
}
