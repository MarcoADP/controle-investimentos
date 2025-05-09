package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.SetorRequest;
import com.github.marcoadp.controle_investimentos.dto.response.SetorResponse;
import com.github.marcoadp.controle_investimentos.entity.Setor;

public class SetorStub {

    public static Setor getSetor() {
        return new Setor(1L, "Setor");
    }

    public static SetorRequest getSetorRequest() {
        return new SetorRequest("Setor");
    }

    public static SetorResponse getSetorResponse() {
        return new SetorResponse("Setor");
    }

}
