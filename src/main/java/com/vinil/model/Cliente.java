package com.vinil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validacao.cpf.obrigatorio}")
    @Size(min = 11, max = 11, message = "{validacao.cpf.tamanho}")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "{validacao.nome.obrigatorio}")
    @Column(nullable = false)
    private String nome;

    private String telefone;
    private String sexo;
    private LocalDate dataNascimento;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Proposta> propostas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public Usuario getUsuario(){return usuario; }
    public void setUsuario(Usuario usuario){this.usuario = usuario; } 
    public List<Proposta> getPropostas() { return propostas; }
    public void setPropostas(List<Proposta> propostas) { this.propostas = propostas; }
}
