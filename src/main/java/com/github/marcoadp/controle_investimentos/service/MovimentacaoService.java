package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;

import java.util.List;

public interface MovimentacaoService {

    Movimentacao criar(MovimentacaoRequest request);

    Movimentacao buscarPeloId(Long id);

    List<Movimentacao> buscarPeloCodigo(String codigo);

    void remover(Long id);

}
