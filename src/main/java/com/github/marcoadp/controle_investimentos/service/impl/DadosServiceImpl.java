package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.AtivoInformacaoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoProventoService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoService;
import com.github.marcoadp.controle_investimentos.service.DadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class DadosServiceImpl implements DadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoService consolidacaoService;

    private final ConsolidacaoProventoService consolidacaoProventoService;

    @Override
    public CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var ativos = carteira.getAtivos().stream()
                .map(ativo -> criarAtivoInformacaoResponse(ativo.getCodigo()))
                .sorted(Comparator.comparing(AtivoInformacaoResponse::tipo).thenComparing(AtivoInformacaoResponse::codigo))
                .toList();
        return new CarteiraSimplificadaResponse(ativos);
    }

    private AtivoInformacaoResponse criarAtivoInformacaoResponse(String codigo) {
        var consolidacao = consolidacaoService.buscarPeloCodigo(codigo);
        var consolidacoesProvento = consolidacaoProventoService.buscarPeloCodigo(codigo);
        var proventoValor = consolidacoesProvento.stream().map(ConsolidacaoProvento::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        return AtivoInformacaoResponse.criar(consolidacao, proventoValor);
    }
}
