package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.response.ProventoHistoricoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoPeriodoResponse;

import java.util.List;

public interface ProventoDadosService {

    ProventoHistoricoResponse buscarProventoHistorico(Long carteiraId, Integer anoInicio, Integer anoFim);

    List<ProventoPeriodoResponse> buscarProventoAnual(Long carteiraId);

    List<ProventoPeriodoResponse> buscarProventoMensal(Long carteiraId, Integer ano);

}
