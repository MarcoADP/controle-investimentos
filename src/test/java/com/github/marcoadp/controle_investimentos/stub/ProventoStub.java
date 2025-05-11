package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.ProventoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoResponse;
import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ProventoStub {

    public static Provento getProvento() {
        return Provento.builder()
                .dataPagamento(LocalDate.of(2025, 1, 1))
                .tipoProvento(TipoProventoEnum.DIVIDENDOS)
                .codigo("ACAO4")
                .quantidade(10)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE.setScale(5, RoundingMode.HALF_UP))
                .build();
    }

    public static ProventoRequest getProventoRequest() {
        return ProventoRequest.builder()
                .dataPagamento(LocalDate.of(2025, 1, 1))
                .tipoProvento("Dividendos")
                .codigo("ACAO4")
                .quantidade(10)
                .valorTotal(BigDecimal.TEN)
                .build();
    }

    public static ProventoResponse getProventoResponse() {
        return ProventoResponse.builder()
                .dataPagamento(LocalDate.of(2025, 1, 1))
                .tipoProvento("Dividendos")
                .codigo("ACAO4")
                .quantidade(10)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE.setScale(5, RoundingMode.HALF_UP))
                .build();
    }

    public static final String PROVENTO_REQUEST_JSON = """
            {
              "dataPagamento": "2025-01-01",
              "tipoProvento": "Dividendos",
              "codigo": "ACAO4",
              "quantidade": 10,
              "valorTotal": 10
            }
    """;

    public static final String PROVENTO_REQUEST_LOTE_JSON = """
            [
                {
                  "dataPagamento": "2025-01-01",
                  "tipoProvento": "Dividendos",
                  "codigo": "ACAO4",
                  "quantidade": 10,
                  "valorTotal": 10
                }
            ]
    """;

}
