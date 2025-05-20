package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.CotacaoHistoricoRequest;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;

import java.time.LocalDate;
import java.util.List;

public interface CotacaoHistoricoService {

    List<CotacaoHistorico> gerarCotacoesManual(List<CotacaoHistoricoRequest> requests);

    List<CotacaoHistorico> gerarCotacoesDia(Long carteiraId);

    CotacaoHistorico buscarPeloId(Long id);

    List<CotacaoHistorico> buscarPeloCodigo(String codigo);

    List<CotacaoHistorico> buscarPelaData(LocalDate data);
    void remover(Long id);
}
