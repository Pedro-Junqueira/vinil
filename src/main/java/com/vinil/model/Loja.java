package com.vinil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "lojas")
public class Loja extends Usuario {

    @NotBlank(message = "{validacao.cnpj.obrigatorio}")
    @Size(min = 14, max = 14, message = "{validacao.cnpj.tamanho}")
    @Pattern(regexp = "\\d*", message = "{validacao.cnpj.formato}")
    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;

    @NotBlank(message = "{validacao.nome.obrigatorio}")
    @Column(nullable = false)
    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("loja")
    private List<Disco> discos;

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Disco> getDiscos() { return discos; }
    public void setDiscos(List<Disco> discos) { this.discos = discos; }
}
