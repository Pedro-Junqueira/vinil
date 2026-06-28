package com.vinil.service;

import com.vinil.model.Disco;
import com.vinil.model.Loja;
import com.vinil.repository.DiscoRepository;
import com.vinil.repository.LojaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DiscoService {

    private final DiscoRepository discoRepository;
    private final LojaRepository lojaRepository;
    private final FileStorageService fileStorageService;

    public DiscoService(
        DiscoRepository discoRepository,
        LojaRepository lojaRepository,
        FileStorageService fileStorageService
    ) {
        this.discoRepository = discoRepository;
        this.lojaRepository = lojaRepository;
        this.fileStorageService = fileStorageService;
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

    public void cadastrar(Disco disco, String emailLoja, MultipartFile foto) {
        Loja loja = lojaRepository.findByEmail(emailLoja);
        disco.setLoja(loja);
        disco.setVendido(false);

        String urlFoto = fileStorageService.salvar(foto);
        if (urlFoto != null) {
            disco.setFotos(List.of(urlFoto));
        }

        discoRepository.save(disco);
    }

    public List<Disco> listarPorLoja(String emailLoja) {
        Loja loja = lojaRepository.findByEmail(emailLoja);
        return discoRepository.findByLoja(loja);
    }

    public Disco cadastrarPorLojaId(Disco disco, Long lojaId) {
        Loja loja = lojaRepository.findById(lojaId).orElseThrow();
        disco.setLoja(loja);
        disco.setVendido(false);
        return discoRepository.save(disco);
    }

    public List<Disco> listarPorLojaId(Long lojaId) {
        return discoRepository.findByLojaId(lojaId);
    }

    public List<Disco> listarPorArtista(String artista) {
        return discoRepository.findByArtista(artista);
    }
}
