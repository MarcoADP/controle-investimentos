package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.mapper.MovimentacaoMapper;
import com.github.marcoadp.controle_investimentos.repository.MovimentacaoRepository;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    @Override
    public Movimentacao criar(MovimentacaoRequest movimentacaoRequest) {
        var movimentacao = movimentacaoMapper.toMovimentacao(movimentacaoRequest);
        return movimentacaoRepository.save(movimentacao);
    }

    @Override
    public List<Movimentacao> criarEmLote(List<MovimentacaoRequest> movimentacaoRequests) {
        var movimentacoes = movimentacaoRequests.stream().map(movimentacaoMapper::toMovimentacao).toList();
        return movimentacaoRepository.saveAll(movimentacoes);
    }

    @Override
    public List<Movimentacao> buscarPeloCodigoEData(String codigo, LocalDate data) {
        return movimentacaoRepository.findByCodigoAndDataLessThanEqual(codigo, data);
    }

    @Override
    public List<Movimentacao> buscarPelaOperacao(OperacaoEnum operacao) {
        return movimentacaoRepository.findByOperacao(operacao);
    }

    @Override
    public List<Movimentacao> buscarTodas() {
        return movimentacaoRepository.findAll();
    }

    @Override
    public Movimentacao buscarPeloId(Long id) {
        return movimentacaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Movimentação", id));
    }

    @Override
    public List<Movimentacao> buscarPeloCodigo(String codigo) {
        return movimentacaoRepository.findByCodigo(codigo);
    }

    @Override
    public void remover(Long id) {
        movimentacaoRepository.deleteById(id);
    }
}
