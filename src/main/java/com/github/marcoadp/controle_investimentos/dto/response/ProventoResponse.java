package com.github.marcoadp.controle_investimentos.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ProventoResponse(
        LocalDate dataPagamento,
        String tipoProvento,
        String codigo,
        Integer quantidade,
        BigDecimal valorTotal,
        BigDecimal valorMedio
) {
}
