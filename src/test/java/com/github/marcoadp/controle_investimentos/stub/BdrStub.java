package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.entity.Bdr;

public class BdrStub {

    public static Bdr getBdr() {

        return Bdr.builder()
                .nome("BDR")
                .codigo("BDRZ32")
                .cnpj("26492737000162")
                .setor("Setor")
                .build();
    }

    public static final String BDR_REQUEST_JSON = """
        {
            "nome": "BDR",
            "codigo": "BDRZ32",
            "cnpj": "26492737000162",
            "setor": "Setor"
        }";
    """;

}
