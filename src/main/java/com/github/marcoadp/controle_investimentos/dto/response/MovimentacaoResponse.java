package com.github.marcoadp.controle_investimentos.dto.response;

import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record MovimentacaoResponse(
        LocalDate data,
        OperacaoEnum operacao,
        String codigo,
        String tipoAtivo,
        BigDecimal quantidade,
        BigDecimal valorUnitario,
        BigDecimal valorTotal
) {
}
