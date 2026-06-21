package com.vinil;

import com.vinil.model.*;
import com.vinil.model.enums.EstadoConservacao;
import com.vinil.model.enums.Role;
import com.vinil.model.enums.StatusProposta;
import com.vinil.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class VinilApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinilApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
		UsuarioRepository usuarioRepo,
		LojaRepository lojaRepo,
		ClienteRepository clienteRepo,
		DiscoRepository discoRepo,
		PropostaRepository propostaRepo,
		PasswordEncoder passwordEncoder
	) {
		return args -> {

			Usuario admin = new Usuario();
			admin.setEmail("admin@vinil.com");
			admin.setSenha(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			usuarioRepo.save(admin);
			System.out.println(">> Admin criado: " + admin.getEmail());

			Usuario usuarioLoja = new Usuario();
			usuarioLoja.setEmail("contato@vinilmania.com");
			usuarioLoja.setSenha(passwordEncoder.encode("loja123"));
			usuarioLoja.setRole(Role.LOJA);
			usuarioRepo.save(usuarioLoja);

			Loja loja = new Loja();
			loja.setNome("Vinil Mania");
			loja.setCnpj("12345678000195");
			loja.setDescricao("A melhor loja de vinil de SP");
			loja.setUsuario(usuarioLoja);
			lojaRepo.save(loja);
			System.out.println(">> Loja criada: " + loja.getNome());

			Usuario usuarioCliente = new Usuario();
			usuarioCliente.setEmail("pedro@email.com");
			usuarioCliente.setSenha(passwordEncoder.encode("cliente123"));
			usuarioCliente.setRole(Role.CLIENTE);
			usuarioRepo.save(usuarioCliente);

			Cliente cliente = new Cliente();
			cliente.setNome("Pedro Limas");
			cliente.setCpf("12345678901");
			cliente.setTelefone("11999999999");
			cliente.setSexo("M");
			cliente.setDataNascimento(LocalDate.of(2000, 1, 15));
			cliente.setUsuario(usuarioCliente);
			clienteRepo.save(cliente);
			System.out.println(">> Cliente criado: " + cliente.getNome());

			Disco disco = new Disco();
			disco.setTitulo("Abbey Road");
			disco.setArtista("The Beatles");
			disco.setGravadora("Apple Records");
			disco.setAnoLancamento(1969);
			disco.setGeneroMusical("Rock");
			disco.setEstadoConservacao(EstadoConservacao.MUITO_BOM);
			disco.setDescricao("Excelente estado, sem riscos");
			disco.setValor(new BigDecimal("250.00"));
			disco.setVendido(false);
			disco.setLoja(loja);
			discoRepo.save(disco);
			System.out.println(">> Disco criado: " + disco.getTitulo());

			Proposta proposta = new Proposta();
			proposta.setValor(new BigDecimal("200.00"));
			proposta.setCondicoesPagamento("PIX a vista");
			proposta.setDataProposta(LocalDate.now());
			proposta.setStatus(StatusProposta.ABERTO);
			proposta.setCliente(cliente);
			proposta.setDisco(disco);
			propostaRepo.save(proposta);
			System.out.println(">> Proposta criada com status: " + proposta.getStatus());

			System.out.println("\n>> Total de usuarios: " + usuarioRepo.count());
			System.out.println(">> Total de lojas: " + lojaRepo.count());
			System.out.println(">> Total de clientes: " + clienteRepo.count());
			System.out.println(">> Total de discos: " + discoRepo.count());
			System.out.println(">> Total de propostas: " + propostaRepo.count());

			disco.setVendido(true);
			discoRepo.save(disco);
			System.out.println("\n>> Disco marcado como vendido: " + disco.getVendido());

			propostaRepo.delete(proposta);
			System.out.println(">> Proposta deletada. Total restante: " + propostaRepo.count());
		};
	}
}
