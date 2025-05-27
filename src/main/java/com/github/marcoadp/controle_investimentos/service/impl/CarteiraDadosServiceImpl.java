package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.AtivoInformacaoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.CarteiraSimplificadaResponse;
import com.github.marcoadp.controle_investimentos.dto.response.DadosResumoResponse;
import com.github.marcoadp.controle_investimentos.dto.response.PatrimonioEvolucaoResponse;
import com.github.marcoadp.controle_investimentos.entity.CarteiraAtivo;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.time.format.TextStyle.SHORT;

@Service
@RequiredArgsConstructor
public class CarteiraDadosServiceImpl implements CarteiraDadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoService consolidacaoService;

    private final ConsolidacaoProventoService consolidacaoProventoService;

    private final CotacaoHistoricoService cotacaoHistoricoService;

    private final ProventoDadosService proventoDadosService;

    private final MovimentacaoService movimentacaoService;

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
    public DadosResumoResponse buscarResumo(Long carteiraId) {
        var carteira = buscarCarteiraSimplificada(carteiraId);
        var proventosPorAno = proventoDadosService.buscarProventoAnual(carteiraId);
        return DadosResumoResponse.calcular(carteira.ativos(), proventosPorAno);
    }

    @Override
    public List<PatrimonioEvolucaoResponse> buscarPatrimonioEvolucao(Long carteiraId, Integer meses) {
        var patrimonios = new ArrayList<PatrimonioEvolucaoResponse>();
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var codigos = carteira.getAtivos().stream().map(CarteiraAtivo::getCodigo).toList();
        var movimentacoes = movimentacaoService.buscarPelaOperacao(OperacaoEnum.ENTRADA).stream()
                .filter(movimentacao -> codigos.contains(movimentacao.getCodigo()))
                .toList();
        meses = Objects.requireNonNullElse(meses, 12);
        for (int i = meses; i >= 0; i--) {
            var data = LocalDate.now().minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
            var cotacoes = cotacaoHistoricoService.buscarCotacaoMaisProxima(data).stream()
                    .filter(cotacao -> codigos.contains(cotacao.getCodigo()))
                    .toList();
            var valor = cotacoes.stream().map(CotacaoHistorico::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            var movFiltered = movimentacoes.stream().filter(movimentacao -> !movimentacao.getData().isAfter(data)).toList();
            var valorInvestido = movFiltered.stream().map(Movimentacao::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            var saldo = valor.subtract(valorInvestido);
            var mes = StringUtils.capitalize(data.getMonth().getDisplayName(SHORT, Locale.getDefault()));
            var periodo = String.format("%s/%s", mes, data.getYear());
            patrimonios.add( new PatrimonioEvolucaoResponse(periodo, valorInvestido, valor, saldo));
        }
        return patrimonios;
    }
}
