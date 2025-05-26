package com.github.marcoadp.controle_investimentos.service;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoService {

    Movimentacao criar(MovimentacaoRequest request);

    Movimentacao buscarPeloId(Long id);

    List<Movimentacao> buscarPeloCodigo(String codigo);

    void remover(Long id);

    List<Movimentacao> criarEmLote(List<MovimentacaoRequest> movimentacaoRequests);

    List<Movimentacao> buscarPeloCodigoEData(String codigo, LocalDate data);

    List<Movimentacao> buscarPelaOperacao(OperacaoEnum operacao);
}
