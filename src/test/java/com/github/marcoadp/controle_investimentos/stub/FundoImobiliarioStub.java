package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario.FundoImobiliarioBuilder;

public class FundoImobiliarioStub {

    public static FundoImobiliarioBuilder getFundoImobiliarioBuilder() {
        return FundoImobiliario.builder()
                .nome("FI")
                .codigo("FIMO11")
                .cnpj("26492737000162")
                .tipo("Tipo")
                .segmento("Segmento");
    }

    public static final String FI_REQUEST_JSON = """
        {
            "nome": "FI",
            "codigo": "FIMO11",
            "cnpj": "26492737000162",
            "tipo": "Tipo",
            "Segmento": "Segmento"
        }";
    """;

}
