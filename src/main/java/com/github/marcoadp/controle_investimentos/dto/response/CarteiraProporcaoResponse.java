package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;

public record CarteiraProporcaoResponse(String descricao, BigDecimal valor, BigDecimal proporcao) {
}
