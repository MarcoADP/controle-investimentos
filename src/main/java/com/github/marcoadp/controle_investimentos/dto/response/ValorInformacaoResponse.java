package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;

public record ValorInformacaoResponse(BigDecimal quantidade, BigDecimal valorMedio, BigDecimal valorTotal) { }
