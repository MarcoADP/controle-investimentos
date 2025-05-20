package com.github.marcoadp.controle_investimentos.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CotacaoHistoricoRequest(String codigo, LocalDate data, BigDecimal valor) {
}
