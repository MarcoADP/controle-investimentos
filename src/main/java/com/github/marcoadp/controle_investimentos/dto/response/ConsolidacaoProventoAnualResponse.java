package com.github.marcoadp.controle_investimentos.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ConsolidacaoProventoAnualResponse(
        String codigo,
        Integer ano,
        BigDecimal valorTotal,
        BigDecimal valorMedio
) {
}
