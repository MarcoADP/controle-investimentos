package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.*;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;
import com.github.marcoadp.controle_investimentos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DadosServiceImpl implements DadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoService consolidacaoService;

    private final ConsolidacaoProventoService consolidacaoProventoService;

    private final CotacaoHistoricoService cotacaoHistoricoService;

    private final AcaoService acaoService;

    private final BdrService bdrService;

    private final EtfService etfService;

    private final FundoImobiliarioService fundoImobiliarioService;

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
        var proventoValor = consolidacoesProvento.stream()
                .map(ConsolidacaoProvento::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var cotacaoOpt = cotacaoHistoricoService.buscarCotacaoMaisRecente(codigo);
        var valorAtual = cotacaoOpt.isPresent() ? cotacaoOpt.get().getValor() : BigDecimal.ZERO;
        return AtivoInformacaoResponse.criar(consolidacao, proventoValor, valorAtual);
    }

    @Override
    public ProventoHistoricoResponse buscarProventoHistorico(Long carteiraId, Integer anoInicio, Integer anoFim) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var proventos = carteira.getAtivos().stream()
                .map(ativo -> criarProventoAnualResponse(ativo.getCodigo(), anoInicio, anoFim))
                .sorted(Comparator.comparing(ProventoHistoricoCodigoResponse::tipo).thenComparing(ProventoHistoricoCodigoResponse::codigo))
                .toList();
        var valorTotal = proventos.stream().map(ProventoHistoricoCodigoResponse::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ProventoHistoricoResponse(valorTotal, proventos);
    }

    private ProventoHistoricoCodigoResponse criarProventoAnualResponse(String codigo, Integer anoInicio, Integer anoFim) {
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
        var ativo = buscarAtivo(codigo);
        return new ProventoHistoricoCodigoResponse(codigo, ativo.tipo(), valorTotal, valorPorAno);
    }

    private AtivoResponse buscarAtivo(String codigo) {
        var acaoOpt = acaoService.buscarPeloCodigo(codigo);
        if (acaoOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.ACAO.getDescricao(), acaoOpt.get().getSetor().getNome());
        }
        var bdrOpt = bdrService.buscarPeloCodigo(codigo);
        if (bdrOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.BDR.getDescricao(), bdrOpt.get().getSetor());
        }
        var etfOpt = etfService.buscarPeloCodigo(codigo);
        if (etfOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.ETF.getDescricao(), etfOpt.get().getTipo());
        }
        var fiOpt = fundoImobiliarioService.buscarPeloCodigo(codigo);
        if (fiOpt.isPresent()) {
            return new AtivoResponse(codigo, TipoAtivoEnum.FI.getDescricao(), fiOpt.get().getCodigo());
        }

        return new AtivoResponse(codigo, "NAO_ENCONTRADO", "NAO_ENCONTRADO");
    }

}
