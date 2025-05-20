package com.github.marcoadp.controle_investimentos.controller;

import com.github.marcoadp.controle_investimentos.dto.request.CotacaoHistoricoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.CotacaoHistoricoResponse;
import com.github.marcoadp.controle_investimentos.mapper.CotacaoHistoricoMapper;
import com.github.marcoadp.controle_investimentos.service.CotacaoHistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cotacao-historico")
@RequiredArgsConstructor
public class CotacaoHistoricoController {

    private final CotacaoHistoricoService cotacaoHistoricoService;
    private final CotacaoHistoricoMapper cotacaoHistoricoMapper;

    @PostMapping("/manual")
    public List<CotacaoHistoricoResponse> gerarCotacoesManual(@RequestBody List<CotacaoHistoricoRequest> requests) {
        var cotacoes = cotacaoHistoricoService.gerarCotacoesManual(requests);
        return cotacoes.stream().map(cotacaoHistoricoMapper::toCotacaoHistoricoResponse).toList();
    }

    @PostMapping("/carteira/{carteiraId}")
    public List<CotacaoHistoricoResponse> gerarCotacoesDia(@PathVariable Long carteiraId) {
        var cotacoes = cotacaoHistoricoService.gerarCotacoesDia(carteiraId);
        return cotacoes.stream().map(cotacaoHistoricoMapper::toCotacaoHistoricoResponse).toList();
    }

    @GetMapping("/{id}")
    public CotacaoHistoricoResponse buscarPeloId(@PathVariable Long id) {
        var cotacaoHistorico = cotacaoHistoricoService.buscarPeloId(id);
        return cotacaoHistoricoMapper.toCotacaoHistoricoResponse(cotacaoHistorico);
    }

    @GetMapping("/codigo/{codigo}")
    public List<CotacaoHistoricoResponse> buscarPeloCodigo(@PathVariable String codigo) {
        var cotacoes = cotacaoHistoricoService.buscarPeloCodigo(codigo);
        return cotacoes.stream().map(cotacaoHistoricoMapper::toCotacaoHistoricoResponse).toList();
    }

    @GetMapping("/data/{date}")
    public List<CotacaoHistoricoResponse> buscarPelaData(@PathVariable LocalDate date) {
        var cotacoes = cotacaoHistoricoService.buscarPelaData(date);
        return cotacoes.stream().map(cotacaoHistoricoMapper::toCotacaoHistoricoResponse).toList();
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cotacaoHistoricoService.remover(id);
    }



}

