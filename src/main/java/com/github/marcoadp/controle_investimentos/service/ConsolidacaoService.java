package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;

import java.util.List;

public interface ConsolidacaoService {

    List<Consolidacao> consolidarTodasMovimentacoes();

    List<Consolidacao> consolidarAtivoPorAno(String codigo);

    List<Consolidacao> consolidarAtivoPorData(Integer mes, Integer ano);

    Consolidacao buscarPeloId(Long id);

    Consolidacao buscarPeloCodigo(String codigo);

    List<Consolidacao> buscarPelaData(Integer mes, Integer ano);

    void remover(Long id);
}
