package com.github.marcoadp.controle_investimentos.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ConsolidacaoProventoResponse(
        String codigo,
        Integer ano,
        BigDecimal valorTotal,
        BigDecimal valorMedio
) {
}
