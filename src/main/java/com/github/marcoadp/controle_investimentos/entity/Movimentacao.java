package com.github.marcoadp.controle_investimentos.entity;

import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
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
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimentacao_id")
    private Long id;

    @Column
    private LocalDate data;

    @Column
    @Enumerated(EnumType.STRING)
    private OperacaoEnum operacao;

    @Column
    private String codigo;

    @Column(name = "tipo_ativo")
    @Enumerated(EnumType.STRING)
    private TipoAtivoEnum tipoAtivo;

    @Column
    private BigDecimal quantidade;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;


}
