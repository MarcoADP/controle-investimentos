package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.ProventoHistoricoCodigoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoHistoricoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.ProventoPeriodoResponse;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.service.AtivoService;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoProventoService;
import com.github.marcoadp.controle_investimentos.service.ProventoDadosService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.TextStyle.SHORT;

@Service
@RequiredArgsConstructor
public class ProventosDadosServiceImpl implements ProventoDadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoProventoService consolidacaoProventoService;

    private final AtivoService ativoService;

    @Override
    public ProventoHistoricoResponse buscarProventoHistorico(Long carteiraId, Integer anoInicio, Integer anoFim) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var proventos = carteira.getAtivos().stream()
                .map(ativo -> criarProventoCodigoResponse(ativo.getCodigo(), anoInicio, anoFim))
                .sorted(Comparator.comparing(ProventoHistoricoCodigoResponse::tipo).thenComparing(ProventoHistoricoCodigoResponse::codigo))
                .toList();
        var valorTotal = proventos.stream().map(ProventoHistoricoCodigoResponse::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ProventoHistoricoResponse(valorTotal, proventos);
    }

    private ProventoHistoricoCodigoResponse criarProventoCodigoResponse(String codigo, Integer anoInicio, Integer anoFim) {
        var consolidacoesProvento = consolidacaoProventoService.buscarPeloCodigo(codigo);
        Map<Integer, List<ConsolidacaoProvento>> consolidacoesMap = consolidacoesProvento.stream()
                .collect(Collectors.groupingBy(ConsolidacaoProvento::getAno));
        Map<Integer, BigDecimal> valorPorAno = new HashMap<>();
        anoInicio = Objects.requireNonNullElse(anoInicio, 2019);
        anoFim = Objects.requireNonNullElse(anoFim, LocalDate.now().getYear());
        for (int ano = anoInicio; ano <= anoFim; ano++) {
            BigDecimal valorTotal = consolidacoesMap.getOrDefault(ano, new ArrayList<>()).stream()
                    .map(ConsolidacaoProvento::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            valorPorAno.put(ano, valorTotal);
        }
        BigDecimal valorTotal = valorPorAno.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        var ativo = ativoService.buscarAtivo(codigo);
        return new ProventoHistoricoCodigoResponse(codigo, ativo.tipo(), valorTotal, valorPorAno);
    }

    @Override
    public List<ProventoPeriodoResponse> buscarProventoAnual(Long carteiraId) {
        var proventos = new ArrayList<ProventoPeriodoResponse>();
        var consolidacoesProvento = consolidacaoProventoService.buscarTodas();
        var consolidacoesAno = consolidacoesProvento.stream().collect(Collectors.groupingBy(ConsolidacaoProvento::getAno));
        for (var entry : consolidacoesAno.entrySet()) {
            var proventoValor = entry.getValue().stream()
                    .map(ConsolidacaoProvento::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            proventos.add(new ProventoPeriodoResponse(entry.getKey().toString(), proventoValor));
        }
        return proventos;
    }

    @Override
    public List<ProventoPeriodoResponse> buscarProventoMensal(Long carteiraId, Integer ano) {
        var proventos = new ArrayList<ProventoPeriodoResponse>();
        ano = Objects.requireNonNullElse(ano, LocalDate.now().getYear());
        var consolidacoesProvento = consolidacaoProventoService.buscarPeloAno(ano);
        var consolidacoesAno = consolidacoesProvento.stream().collect(Collectors.groupingBy(ConsolidacaoProvento::getMes));
        for (var entry : consolidacoesAno.entrySet()) {
            var proventoValor = entry.getValue().stream()
                    .map(ConsolidacaoProvento::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            var mes = StringUtils.capitalize(Month.of(entry.getKey()).getDisplayName(SHORT, Locale.getDefault()));
            var periodo = String.format("%s/%s", mes, ano);
            proventos.add(new ProventoPeriodoResponse(periodo, proventoValor));
        }
        return proventos;
    }
}
