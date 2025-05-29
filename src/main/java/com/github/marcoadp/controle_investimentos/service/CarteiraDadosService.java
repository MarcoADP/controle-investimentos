package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraProporcaoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.dto.response.DadosResumoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.PatrimonioEvolucaoResponse;

import java.util.List;

public interface CarteiraDadosService {

    CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId);

    DadosResumoResponse buscarResumo(Long carteiraId);

    List<PatrimonioEvolucaoResponse> buscarPatrimonioEvolucao(Long carteiraId, Integer meses);

    List<CarteiraProporcaoResponse> buscarProporcaoCarteira(Long carteiraId);

    List<CarteiraProporcaoResponse> buscarProporcaoAtivos(Long carteiraId, List<String> tipos);
}
