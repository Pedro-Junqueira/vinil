package com.vinil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @NotBlank(message = "{validacao.cpf.obrigatorio}")
    @Size(min = 11, max = 11, message = "{validacao.cpf.tamanho}")
    @Pattern(regexp = "\\d*", message = "{validacao.cpf.formato}")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotBlank(message = "{validacao.nome.obrigatorio}")
    @Column(nullable = false)
    private String nome;

    private String telefone;
    private String sexo;
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Proposta> propostas;

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
    public List<Proposta> getPropostas() { return propostas; }
    public void setPropostas(List<Proposta> propostas) { this.propostas = propostas; }
}
