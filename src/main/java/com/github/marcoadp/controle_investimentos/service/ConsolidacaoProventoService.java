package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;

import java.util.List;

public interface ConsolidacaoProventoService {

    List<ConsolidacaoProvento> consolidarCarteiras();

    ConsolidacaoProvento buscarPeloId(Long id);

    List<ConsolidacaoProvento> buscarPeloCodigo(String codigo);

    List<ConsolidacaoProvento> buscarPeloCodigoEAno(String codigo, int ano);

    void remover(Long id);
}
