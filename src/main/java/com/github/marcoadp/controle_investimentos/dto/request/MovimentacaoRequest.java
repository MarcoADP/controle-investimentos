package com.github.marcoadp.controle_investimentos.dto.request;

import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record MovimentacaoRequest(
        LocalDate data,
        OperacaoEnum operacao,
        String codigo,
        TipoAtivoEnum tipoAtivo,
        BigDecimal quantidade,
        BigDecimal valorUnitario
) {
}
