package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.BdrRequest;
import com.github.marcoadp.controle_investimentos.dto.response.BdrResponse;
import com.github.marcoadp.controle_investimentos.entity.Bdr;

public class BdrStub {

    public static Bdr getBdr(Long id) {
        return Bdr.builder().id(id).nome("BDR").codigo("BDRZ32").cnpj("26492737000162").setor("Setor").build();
    }

    public static BdrRequest getBdrRequest() {
        return new BdrRequest("BDR", "BDRZ32", "26492737000162", "Setor");
    }

    public static BdrResponse getBdrResponse() {
        return new BdrResponse("BDR", "BDRZ32", "26492737000162", "Setor");
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
