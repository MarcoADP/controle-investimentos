package com.github.marcoadp.controle_investimentos.dto.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ProventoRequest(
        LocalDate dataPagamento,
        String tipoProvento,
        String codigo,
        Integer quantidade,
        BigDecimal valorTotal
) {
}
