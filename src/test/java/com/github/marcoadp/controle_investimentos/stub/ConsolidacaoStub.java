package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;

import java.math.BigDecimal;

public class ConsolidacaoStub {

    public static Consolidacao getConsolidacao() {
        Consolidacao consolidacao = Consolidacao.builder()
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO)
                .build();
        consolidacao.calcularEntrada(BigDecimal.TEN, BigDecimal.TEN);
        consolidacao.calcularSaida(BigDecimal.ZERO, BigDecimal.ZERO);
        consolidacao.calcularLucro();
        return consolidacao;
    }

}
