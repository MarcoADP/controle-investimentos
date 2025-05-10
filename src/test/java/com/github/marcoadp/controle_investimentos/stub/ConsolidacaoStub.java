package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    
    public static ConsolidacaoResponse getConsolidacaoResponse() {
        return ConsolidacaoResponse.builder()
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO.getDescricao())
                .quantidadeEntrada(BigDecimal.TEN)
                .valorMedioEntrada(BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP))
                .valorTotalEntrada(BigDecimal.TEN)
                .quantidadeSaida(BigDecimal.ZERO)
                .valorMedioSaida(BigDecimal.ZERO)
                .valorTotalSaida(BigDecimal.ZERO)
                .lucroMedio(BigDecimal.ZERO)
                .lucroProporcao(BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP))
                .build();
    }

}
