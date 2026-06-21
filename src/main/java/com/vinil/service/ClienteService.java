package com.vinil.service;

import com.vinil.model.Cliente;
import com.vinil.model.Usuario;
import com.vinil.model.enums.Role;
import com.vinil.repository.ClienteRepository;
import com.vinil.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(
        ClienteRepository clienteRepository,
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    public void criar(Cliente cliente, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setRole(Role.CLIENTE);
        usuarioRepository.save(usuario);

        cliente.setUsuario(usuario);
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
