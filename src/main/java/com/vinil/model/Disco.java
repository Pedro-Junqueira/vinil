package com.vinil.model;

import com.vinil.model.enums.EstadoConservacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "discos")
public class Disco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    private String gravadora;
    private Integer anoLancamento;
    private String generoMusical;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConservacao estadoConservacao;

    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @ElementCollection
    @CollectionTable(name = "disco_fotos", joinColumns = @JoinColumn(name = "disco_id"))
    @Column(name = "url_foto")
    private List<String> fotos;

    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;

    @OneToMany(mappedBy = "disco", cascade = CascadeType.ALL)
    private List<Proposta> propostas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getGravadora() { return gravadora; }
    public void setGravadora(String gravadora) { this.gravadora = gravadora; }
    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }
    public String getGeneroMusical() { return generoMusical; }
    public void setGeneroMusical(String generoMusical) { this.generoMusical = generoMusical; }
    public EstadoConservacao getEstadoConservacao() { return estadoConservacao; }
    public void setEstadoConservacao(EstadoConservacao estadoConservacao) { this.estadoConservacao = estadoConservacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public List<String> getFotos() { return fotos; }
    public void setFotos(List<String> fotos) { this.fotos = fotos; }
    public Loja getLoja() { return loja; }
    public void setLoja(Loja loja) { this.loja = loja; }
    public List<Proposta> getPropostas() { return propostas; }
    public void setPropostas(List<Proposta> propostas) {this.propostas = propostas; }
}