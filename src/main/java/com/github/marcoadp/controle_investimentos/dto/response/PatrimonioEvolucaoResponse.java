package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;

public record PatrimonioEvolucaoResponse(String Periodo, BigDecimal valorInvestido, BigDecimal valor, BigDecimal saldo) {
}
