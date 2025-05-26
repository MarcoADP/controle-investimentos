package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.CotacaoHistoricoRequest;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CotacaoHistoricoService {

    List<CotacaoHistorico> gerarCotacoesManual(List<CotacaoHistoricoRequest> requests);

    List<CotacaoHistorico> gerarCotacoesDia(Long carteiraId);

    CotacaoHistorico buscarPeloId(Long id);

    List<CotacaoHistorico> buscarPeloCodigo(String codigo);

    List<CotacaoHistorico> buscarPelaData(LocalDate data);

    Optional<CotacaoHistorico> buscarCotacaoMaisRecente(String codigo);

    void remover(Long id);

    List<CotacaoHistorico> buscarCotacaoMaisProxima(LocalDate data);
}
