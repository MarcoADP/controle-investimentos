package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProventoHistoricoResponse(BigDecimal valorTotal, List<ProventoHistoricoCodigoResponse> proventos) {
}
