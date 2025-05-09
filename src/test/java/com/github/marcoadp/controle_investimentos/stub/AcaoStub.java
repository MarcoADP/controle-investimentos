package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.AcaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.AcaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Acao;

import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetor;

public class AcaoStub {

    public static Acao getAcao() {

        return new Acao(1L, "Acao", "ACAO4", "26492737000162", getSetor());
    }

    public static AcaoRequest getAcaoRequest() {
        return new AcaoRequest("Acao", "ACAO4", "26492737000162", getSetor().getId());
    }

    public static AcaoResponse getAcaoResponse() {
        return new AcaoResponse("Acao", "ACAO4", "26492737000162", getSetor().getNome());
    }

}
