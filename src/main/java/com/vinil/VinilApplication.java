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
import java.util.List;

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
			if (usuarioRepo.count() > 0) {
				System.out.println(">> Dados iniciais ja cadastrados.");
				return;
			}

			Usuario admin = new Usuario();
			admin.setEmail("admin@vinil.com");
			admin.setSenha(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			usuarioRepo.save(admin);
			System.out.println(">> Admin criado: " + admin.getEmail());

			Loja loja = new Loja();
			loja.setEmail("contato@vinilmania.com");
			loja.setSenha(passwordEncoder.encode("loja123"));
			loja.setRole(Role.LOJA);
			loja.setNome("Vinil Mania");
			loja.setCnpj("12345678000195");
			loja.setDescricao("A melhor loja de vinil de SP");
			lojaRepo.save(loja);
			System.out.println(">> Loja criada: " + loja.getNome());

			Cliente cliente = new Cliente();
			cliente.setEmail("pedro@email.com");
			cliente.setSenha(passwordEncoder.encode("cliente123"));
			cliente.setRole(Role.CLIENTE);
			cliente.setNome("Pedro Limas");
			cliente.setCpf("12345678901");
			cliente.setTelefone("11999999999");
			cliente.setSexo("M");
			cliente.setDataNascimento(LocalDate.of(2000, 1, 15));
			clienteRepo.save(cliente);
			System.out.println(">> Cliente criado: " + cliente.getNome());

			List<Disco> discos = List.of(
				criarDisco(loja, "Abbey Road", "The Beatles", "Apple Records", 1969, "Rock", EstadoConservacao.MUITO_BOM, "Excelente estado, sem riscos aparentes.", "250.00", false, "https://imusic.b-cdn.net/images/item/original/637/0602478627637.jpg?the-beatles-2025-abbey-road-lp&class=original&v=1760531187"),
				criarDisco(loja, "The Dark Side of the Moon", "Pink Floyd", "Harvest", 1973, "Progressive Rock", EstadoConservacao.MUITO_BOM, "Edicao classica com capa preservada.", "300.00", false, "https://http2.mlstatic.com/D_852151-MLA108577663997_032026-C.jpg"),
				criarDisco(loja, "Thriller", "Michael Jackson", "Epic", 1982, "Pop", EstadoConservacao.BOM, "Vinil revisado, com leves marcas de uso.", "180.00", false, "https://m.media-amazon.com/images/I/71ZvPlNUGPL._AC_SX679_.jpg"),
				criarDisco(loja, "Back in Black", "AC/DC", "Atlantic", 1980, "Hard Rock", EstadoConservacao.MUITO_BOM, "Otima opcao para colecionadores de rock.", "220.00", true, "https://drownedworldrecords.com/cdn/shop/files/DC-Back-In-Black-_Black-_-White-Marble-Vinyl_-257251132.jpg?v=1762272171"),
				criarDisco(loja, "Rumours", "Fleetwood Mac", "Warner Bros.", 1977, "Rock", EstadoConservacao.BOM, "Capa com pequenos sinais do tempo.", "190.00", false, "https://imusic.b-cdn.net/images/item/original/585/0081227815585.jpg?fleetwood-mac-2024-rumours-lp&class=scaled&v=1715389168"),
				criarDisco(loja, "Nevermind", "Nirvana", "DGC", 1991, "Grunge", EstadoConservacao.MUITO_BOM, "Som limpo e encarte conservado.", "210.00", false, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQinUMUE9Ux54pzDniVAKt30nrfSbXb6GxhB2YXJ0_yeA&s=10"),
				criarDisco(loja, "Kind of Blue", "Miles Davis", "Columbia", 1959, "Jazz", EstadoConservacao.MUITO_BOM, "Album essencial de jazz em bela conservacao.", "270.00", false, "https://m.media-amazon.com/images/I/71EvtOII3iL._AC_SX679_.jpg"),
				criarDisco(loja, "Blue Train", "John Coltrane", "Blue Note", 1958, "Jazz", EstadoConservacao.BOM, "Classico do jazz com boa qualidade sonora.", "260.00", false, "https://http2.mlstatic.com/D_Q_NP_669284-CBT111153075035_052026-O.webp"),
				criarDisco(loja, "Clube da Esquina", "Milton Nascimento e Lo Borges", "EMI", 1972, "MPB", EstadoConservacao.MUITO_BOM, "Um dos grandes discos da musica brasileira.", "320.00", false, "https://images.tcdn.com.br/img/img_prod/435678/3618_0_20180130154414.jpg"),
				criarDisco(loja, "Construção", "Chico Buarque", "Philips", 1971, "MPB", EstadoConservacao.BOM, "Disco historico da MPB, bem preservado.", "240.00", true, "https://images.tcdn.com.br/img/img_prod/435678/4922_0_20180507104558.jpg"),
				criarDisco(loja, "Transpiração contínua e prolongada", "Charlie Brown JR", "EMI Brasil", 1997, "ROCK / MPB", EstadoConservacao.NOVO, "Lacrado", "310.00", false, "/uploads/ae532cec-a3f5-4e6b-8afb-25521f542d92.webp")
			);

			discoRepo.saveAll(discos);
			System.out.println(">> Discos criados: " + discos.size());

			Proposta proposta = new Proposta();
			proposta.setValor(new BigDecimal("200.00"));
			proposta.setCondicoesPagamento("PIX a vista");
			proposta.setDataProposta(LocalDate.now());
			proposta.setStatus(StatusProposta.ABERTO);
			proposta.setCliente(cliente);
			proposta.setDisco(discos.get(0));
			propostaRepo.save(proposta);
			System.out.println(">> Proposta criada com status: " + proposta.getStatus());

			System.out.println("\n>> Total de usuarios: " + usuarioRepo.count());
			System.out.println(">> Total de lojas: " + lojaRepo.count());
			System.out.println(">> Total de clientes: " + clienteRepo.count());
			System.out.println(">> Total de discos: " + discoRepo.count());
			System.out.println(">> Total de propostas: " + propostaRepo.count());
		};
	}

	private Disco criarDisco(
		Loja loja,
		String titulo,
		String artista,
		String gravadora,
		Integer anoLancamento,
		String generoMusical,
		EstadoConservacao estadoConservacao,
		String descricao,
		String valor,
		Boolean vendido,
		String foto
	) {
		Disco disco = new Disco();
		disco.setTitulo(titulo);
		disco.setArtista(artista);
		disco.setGravadora(gravadora);
		disco.setAnoLancamento(anoLancamento);
		disco.setGeneroMusical(generoMusical);
		disco.setEstadoConservacao(estadoConservacao);
		disco.setDescricao(descricao);
		disco.setValor(new BigDecimal(valor));
		disco.setVendido(vendido);
		disco.setLoja(loja);
		disco.setFotos(List.of(foto));
		return disco;
	}
}
