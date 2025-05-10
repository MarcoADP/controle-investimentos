package com.github.marcoadp.controle_investimentos.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ConsolidacaoResponse(
        String codigo,
        String tipoAtivo,
        BigDecimal quantidadeEntrada,
        BigDecimal valorMedioEntrada,
        BigDecimal valorTotalEntrada,
        BigDecimal quantidadeSaida,
        BigDecimal valorMedioSaida,
        BigDecimal valorTotalSaida,
        BigDecimal lucroMedio,
        BigDecimal lucroProporcao
) {
}
