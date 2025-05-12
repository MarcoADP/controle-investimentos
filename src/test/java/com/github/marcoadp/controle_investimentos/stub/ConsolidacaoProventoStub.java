package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;

import java.math.BigDecimal;

public class ConsolidacaoProventoStub {

    public static ConsolidacaoProvento getConsolidacaoProvento() {
        ConsolidacaoProvento consolidacao = ConsolidacaoProvento.builder()
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO)
                .ano(2025)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE)
                .build();
        return consolidacao;
    }
    
    public static ConsolidacaoProventoResponse getConsolidacaoProventoResponse() {
        return ConsolidacaoProventoResponse.builder()
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO.getDescricao())
                .ano(2025)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE)
                .build();
    }

}
