package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.CarteiraAtivoRequest;
import com.github.marcoadp.controle_investimentos.dto.request.CarteiraRequest;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraAtivoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraResponse;
import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.entity.CarteiraAtivo;

import java.util.List;

public class CarteiraStub {

    public static Carteira getCarteira() {
        var ativo = CarteiraAtivo.builder().codigo("ACAO4").build();
        return new Carteira(1L, "Carteira", "Carteira de Ativos", List.of(ativo));
    }

    public static CarteiraRequest getCarteiraRequest() {
        var ativo = new CarteiraAtivoRequest("ACAO4");
        return new CarteiraRequest("Carteira", "Carteira de Ativos", List.of(ativo));
    }

    public static CarteiraResponse getCarteiraResponse() {
        var ativo = new CarteiraAtivoResponse("ACAO4");
        return new CarteiraResponse("Carteira", "Carteira de Ativos", List.of(ativo));
    }

    public static final String CARTEIRA_REQUEST_JSON = """
            {
                "nome": "Carteira",
                "descricao": "Carteira de Ativos",
                "ativos": [
                    {
                        "codigo": "ACAO4"
                    }
                ]
            }";
    """;

}
