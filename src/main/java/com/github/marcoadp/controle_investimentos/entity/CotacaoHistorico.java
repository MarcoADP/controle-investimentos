package com.github.marcoadp.controle_investimentos.entity;

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

    public CotacaoHistorico(String codigo, LocalDate data) {
        this.codigo = codigo;
        this.data = data;
    }

    public void calcular(Integer quantidade, BigDecimal valor, BigDecimal valorCompra) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTotal = valor.multiply(BigDecimal.valueOf(quantidade));
        this.valorCompra = valorCompra;
        this.variacao = valorCompra.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : valor.divide(valorCompra, 5, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
    }

}
