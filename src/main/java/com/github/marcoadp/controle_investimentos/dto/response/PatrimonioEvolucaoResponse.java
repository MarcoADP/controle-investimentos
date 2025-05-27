package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;

public record PatrimonioEvolucaoResponse(String periodo, BigDecimal valorInvestido, BigDecimal valor, BigDecimal saldo) {
}
