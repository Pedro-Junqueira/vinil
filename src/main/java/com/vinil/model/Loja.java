package com.vinil.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lojas")
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL)
    private List<Disco> discos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Disco> getDiscos() { return discos; }
    public void setDiscos(List<Disco> discos) { this.discos = discos; }
}