package com.github.marcoadp.controle_investimentos.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CotacaoHistoricoResponse(
        String codigo,
        String data,
        Integer quantidade,
        BigDecimal valor,
        BigDecimal valorTotal,
        BigDecimal valorCompra,
        BigDecimal variacao
) {
}
