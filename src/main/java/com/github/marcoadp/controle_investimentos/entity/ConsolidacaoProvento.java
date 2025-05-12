package com.github.marcoadp.controle_investimentos.entity;

import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ConsolidacaoProvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consolidacao_provento_id")
    private Long id;

    @Column
    private String codigo;

    private Integer ano;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoProventoEnum tipoProvento;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "valor_medio")
    private BigDecimal valorMedio;

    public void inserirValores(BigDecimal valorTotal, BigDecimal valorMedio) {
        this.valorTotal = valorTotal;
        this.valorMedio = valorMedio;
    }
}
