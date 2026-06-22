package com.vinil.service;

import com.vinil.model.Cliente;
import com.vinil.model.enums.Role;
import com.vinil.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
        ClienteRepository clienteRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    public void criar(Cliente cliente, String email, String senha) {
        cliente.setEmail(email);
        cliente.setSenha(passwordEncoder.encode(senha));
        cliente.setRole(Role.CLIENTE);
        clienteRepository.save(cliente);
    }

    public void atualizar(Long id, Cliente dadosNovos) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        cliente.setNome(dadosNovos.getNome());
        cliente.setCpf(dadosNovos.getCpf());
        cliente.setTelefone(dadosNovos.getTelefone());
        cliente.setSexo(dadosNovos.getSexo());
        cliente.setDataNascimento(dadosNovos.getDataNascimento());
        clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
