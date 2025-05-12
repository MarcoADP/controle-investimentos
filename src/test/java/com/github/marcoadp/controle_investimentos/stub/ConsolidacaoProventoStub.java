package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;

import java.math.BigDecimal;

public class ConsolidacaoProventoStub {

    public static ConsolidacaoProvento getConsolidacaoProvento() {
        return ConsolidacaoProvento.builder()
                .codigo("ACAO4")
                .ano(2025)
                .tipoProvento(TipoProventoEnum.DIVIDENDOS)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE)
                .build();
    }
    
    public static ConsolidacaoProventoResponse getConsolidacaoProventoResponse() {
        return ConsolidacaoProventoResponse.builder()
                .codigo("ACAO4")
                .ano(2025)
                .tipoProvento(TipoProventoEnum.DIVIDENDOS)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE)
                .build();
    }

}
