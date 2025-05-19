package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CotacaoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cotacao_historico_id")
    private Long id;

    @Column
    private String codigo;

    @Column(name = "data_preco")
    private LocalDate data;

    private Integer quantidade;

    private BigDecimal valor;

    @Column(name = "valor_compra")
    private BigDecimal valorCompra;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private BigDecimal variacao;

}
