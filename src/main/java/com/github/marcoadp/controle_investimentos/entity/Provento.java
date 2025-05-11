package com.github.marcoadp.controle_investimentos.entity;

import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Provento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provento_id")
    private Long id;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoProventoEnum tipoProvento;

    @Column
    private String codigo;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_medio")
    private BigDecimal valorMedio;

    public void calcularValor(Integer quantidade, BigDecimal valorTotal) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.valorMedio = valorTotal.divide(BigDecimal.valueOf(quantidade), 5, RoundingMode.HALF_UP);
    }

}
