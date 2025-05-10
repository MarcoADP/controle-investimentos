package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;

import java.util.List;

public interface ConsolidacaoService {

    List<Consolidacao> consolidarCarteiras();

    Consolidacao buscarPeloId(Long id);

    Consolidacao buscarPeloCodigo(String codigo);

    void remover(Long id);
}
