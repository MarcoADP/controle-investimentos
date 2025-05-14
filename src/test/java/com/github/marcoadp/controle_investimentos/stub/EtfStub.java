package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.EtfRequest;
import com.github.marcoadp.controle_investimentos.dto.response.EtfResponse;
import com.github.marcoadp.controle_investimentos.entity.Etf;

public class EtfStub {

    public static Etf getEtf(Long id) {
        return Etf.builder().id(id).nome("ETF").codigo("ETF11").cnpj("26492737000162").tipo("Tipo").build();
    }

    public static EtfRequest getEtfRequest() {
        return new EtfRequest("ETF", "ETF11", "26492737000162", "Tipo");
    }

    public static EtfResponse getEtfResponse() {
        return new EtfResponse("ETF", "ETF11", "26492737000162", "Tipo");
    }

    public static final String ETF_REQUEST_JSON = """
        {
            "nome": "ETF",
            "codigo": "ETF11",
            "cnpj": "26492737000162",
            "tipo": "Tipo"
        }";
    """;

}
