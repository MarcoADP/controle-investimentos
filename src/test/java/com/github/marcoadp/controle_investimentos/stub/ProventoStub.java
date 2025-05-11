package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ProventoStub {

    public static Provento getProvento() {
        Provento provento = Provento.builder()
                .dataPagamento(LocalDate.of(2025, 1, 1))
                .tipoProvento(TipoProventoEnum.DIVIDENDOS)
                .codigo("ACAO4")
                .quantidade(10)
                .valorTotal(BigDecimal.TEN)
                .valorMedio(BigDecimal.ONE.setScale(5, RoundingMode.HALF_UP))
                .build();
        return provento;
    }

}
