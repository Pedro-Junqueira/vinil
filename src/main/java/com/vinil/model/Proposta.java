package com.vinil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vinil.model.enums.StatusProposta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String condicoesPagamento;

    @Column(nullable = false)
    private LocalDate dataProposta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProposta status;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("propostas")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "disco_id", nullable = false)
    @JsonIgnoreProperties("propostas")
    private Disco disco;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getCondicoesPagamento() { return condicoesPagamento; }
    public void setCondicoesPagamento(String condicoesPagamento) { this.condicoesPagamento = condicoesPagamento; }
    public LocalDate getDataProposta() { return dataProposta; }
    public void setDataProposta(LocalDate dataProposta) { this.dataProposta = dataProposta; }
    public StatusProposta getStatus() { return status; }
    public void setStatus(StatusProposta status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Disco getDisco() { return disco; }
    public void setDisco(Disco disco) { this.disco = disco; }
}
