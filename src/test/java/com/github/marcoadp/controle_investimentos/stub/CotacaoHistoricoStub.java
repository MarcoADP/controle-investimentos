package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CotacaoHistoricoStub {

    public static CotacaoHistorico getCotacaoHistorico() {
        CotacaoHistorico cotacao = CotacaoHistorico.builder()
                .codigo("ACAO4")
                .valor(BigDecimal.TEN)
                .data(LocalDate.parse("2024-01-01"))
                .quantidade(10)
                .valorTotal(BigDecimal.valueOf(100L))
                .valorCompra(BigDecimal.valueOf(90L))
                .variacao(BigDecimal.valueOf(0.01))
                .build();
        return cotacao;
    }

}
