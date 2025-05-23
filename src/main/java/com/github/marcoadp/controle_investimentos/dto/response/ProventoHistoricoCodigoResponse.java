package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public record ProventoHistoricoCodigoResponse(String codigo,
                                              String tipo,
                                              BigDecimal valorTotal,
                                              Map<Integer, BigDecimal> valorPorAno
) {
}
