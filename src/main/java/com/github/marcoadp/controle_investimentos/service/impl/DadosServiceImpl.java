package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.AtivoInformacaoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.entity.CarteiraAtivo;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoService;
import com.github.marcoadp.controle_investimentos.service.DadosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class DadosServiceImpl implements DadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoService consolidacaoService;

    @Override
    public CarteiraSimplificadaResponse buscarCarteiraSimplificada(Long carteiraId) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var codigos = carteira.getAtivos().stream().map(CarteiraAtivo::getCodigo).toList();
        var consolidacoes = codigos.stream().map(consolidacaoService::buscarPeloCodigo).toList();
        var ativos = consolidacoes.stream().map(AtivoInformacaoResponse::criar)
                .sorted(Comparator.comparing(AtivoInformacaoResponse::tipo).thenComparing(AtivoInformacaoResponse::codigo))
                .toList();
        return new CarteiraSimplificadaResponse(ativos);
    }
}
