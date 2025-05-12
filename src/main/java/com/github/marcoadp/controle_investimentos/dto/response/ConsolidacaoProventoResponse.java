package com.github.marcoadp.controle_investimentos.dto.response;

import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ConsolidacaoProventoResponse(
        String codigo,
        Integer ano,
        TipoProventoEnum tipoProvento,
        BigDecimal valorTotal,
        BigDecimal valorMedio
) {
}
