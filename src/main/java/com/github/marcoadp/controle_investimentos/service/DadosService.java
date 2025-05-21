package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoPeriodoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoHistoricoResponse;

import java.util.List;

public interface DadosService {

    CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId);

    ProventoHistoricoResponse buscarProventoHistorico(Long carteiraId, Integer anoInicio, Integer anoFim);

    List<ProventoPeriodoResponse> buscarProventoAnual(Long carteiraId);

    List<ProventoPeriodoResponse> buscarProventoMensal(Long carteiraId, Integer ano);
}
