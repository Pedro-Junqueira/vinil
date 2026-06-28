package com.vinil.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path diretorioUploads = Paths.get("src/main/resources/static/uploads");

    public String salvar(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }

        try {
            String nomeOriginal = StringUtils.cleanPath(arquivo.getOriginalFilename());
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeUnico = UUID.randomUUID() + extensao;

            Path destino = diretorioUploads.resolve(nomeUnico);
            Files.copy(arquivo.getInputStream(), destino);

            return "/uploads/" + nomeUnico;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo de imagem", e);
        }
    }
}
