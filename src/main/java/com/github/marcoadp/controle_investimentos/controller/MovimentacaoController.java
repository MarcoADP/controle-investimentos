package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.MovimentacaoResponse;
import com.github.marcoadp.controle_investimentos.mapper.MovimentacaoMapper;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacao")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoMapper movimentacaoMapper;

    @PostMapping
    public MovimentacaoResponse criar(@RequestBody MovimentacaoRequest movimentacaoRequest) {
        var movimentacao = movimentacaoService.criar(movimentacaoRequest);
        return movimentacaoMapper.toMovimentacaoResponse(movimentacao);
    }

    @PostMapping("/lote")
    public List<MovimentacaoResponse> criarEmLote(@RequestBody List<MovimentacaoRequest> movimentacaoRequests) {
        var movimentacoes = movimentacaoService.criarEmLote(movimentacaoRequests);
        return movimentacoes.stream().map(movimentacaoMapper::toMovimentacaoResponse).toList();
    }

    @GetMapping("/{id}")
    public MovimentacaoResponse buscarPeloId(@PathVariable Long id) {
        var movimentacao = movimentacaoService.buscarPeloId(id);
        return movimentacaoMapper.toMovimentacaoResponse(movimentacao);
    }

    @GetMapping("/codigo/{codigo}")
    public List<MovimentacaoResponse> buscarPeloCodigo(@PathVariable String codigo) {
        var movimentacoes = movimentacaoService.buscarPeloCodigo(codigo);
        return movimentacoes.stream().map(movimentacaoMapper::toMovimentacaoResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        movimentacaoService.remover(id);
    }



}

