package com.vinil;

import com.vinil.model.*;
import com.vinil.model.enums.EstadoConservacao;
import com.vinil.model.enums.StatusProposta;
import com.vinil.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class VinilApplication {

	public static void main(String[] args) {
		SpringApplication.run(VinilApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
		LojaRepository lojaRepo,
		ClienteRepository clienteRepo,
		DiscoRepository discoRepo,
		PropostaRepository propostaRepo
	) {
		return args -> {

			// CREATE - Loja
			Loja loja = new Loja();
			loja.setNome("Sonzera Vinil");
			loja.setEmail("contato@sonzeravinil.com");
			loja.setSenha("123456");
			loja.setCnpj("12345678000195");
			loja.setDescricao("A melhor loja de vinil de SP");
			lojaRepo.save(loja);
			System.out.println(">> Loja criada: " + loja.getNome());

			// CREATE - Cliente
			Cliente cliente = new Cliente();
			cliente.setNome("Pedro Limas");
			cliente.setEmail("pedro@email.com");
			cliente.setSenha("123456");
			cliente.setCpf("12345678901");
			cliente.setTelefone("11999999999");
			cliente.setSexo("M");
			cliente.setDataNascimento(LocalDate.of(2000, 1, 15));
			clienteRepo.save(cliente);
			System.out.println(">> Cliente criado: " + cliente.getNome());

			// CREATE - Disco
			Disco disco = new Disco();
			disco.setTitulo("Abbey Road");
			disco.setArtista("The Beatles");
			disco.setGravadora("Apple Records");
			disco.setAnoLancamento(1969);
			disco.setGeneroMusical("Rock");
			disco.setEstadoConservacao(EstadoConservacao.MUITO_BOM);
			disco.setDescricao("Excelente estado, sem riscos");
			disco.setValor(new BigDecimal("350.00"));
			disco.setLoja(loja);
			discoRepo.save(disco);
			System.out.println(">> Disco criado: " + disco.getTitulo());

			// CREATE - Proposta
			Proposta proposta = new Proposta();
			proposta.setValor(new BigDecimal("200.00"));
			proposta.setCondicoesPagamento("PIX à vista");
			proposta.setDataProposta(LocalDate.now());
			proposta.setStatus(StatusProposta.ABERTO);
			proposta.setCliente(cliente);
			proposta.setDisco(disco);
			propostaRepo.save(proposta);
			System.out.println(">> Proposta criada com status: " + proposta.getStatus());

			// READ
			System.out.println("\n>> Total de lojas: " + lojaRepo.count());
			System.out.println(">> Total de clientes: " + clienteRepo.count());
			System.out.println(">> Total de discos: " + discoRepo.count());
			System.out.println(">> Total de propostas: " + propostaRepo.count());

			// UPDATE
			loja.setNome("Sonzera Vinil SP");
			lojaRepo.save(loja);
			System.out.println("\n>> Loja atualizada: " + loja.getNome());

			// DELETE
			propostaRepo.delete(proposta);
			System.out.println(">> Proposta deletada. Total restante: " + propostaRepo.count());
		};
	}
}