package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.*;
import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import com.github.marcoadp.controle_investimentos.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.TextStyle.SHORT;

@Service
@RequiredArgsConstructor
public class CarteiraDadosServiceImpl implements CarteiraDadosService {

    private final CarteiraService carteiraService;

    private final ConsolidacaoService consolidacaoService;

    private final ConsolidacaoProventoService consolidacaoProventoService;

    private final CotacaoHistoricoService cotacaoHistoricoService;

    private final ProventoDadosService proventoDadosService;

    private final AtivoService ativoService;

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
        var consolidacao = consolidacaoService.buscarUltimaConsolidacao(codigo);
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
        meses = Objects.requireNonNullElse(meses, 12);
        for (int i = meses; i >= 0; i--) {
            var data = LocalDate.now().minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
            var cotacoes = cotacaoHistoricoService.buscarCotacaoMaisProxima(data).stream().toList();
            var valor = cotacoes.stream().map(CotacaoHistorico::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            var consolidacoes = consolidacaoService.buscarPelaData(data.getMonthValue(), data.getYear());
            var valorInvestido = consolidacoes.stream()
                    .map(Consolidacao::getValorTotalEntrada)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            var valorVenda = consolidacoes.stream()
                    .map(Consolidacao::getValorTotalSaida)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            valor = valor.add(valorVenda);
            var saldo = valor.subtract(valorInvestido);
            var mes = StringUtils.capitalize(data.getMonth().getDisplayName(SHORT, Locale.getDefault()));
            var periodo = String.format("%s/%s", mes, data.getYear());
            patrimonios.add( new PatrimonioEvolucaoResponse(periodo, valorInvestido, valor, saldo));
        }
        return patrimonios;
    }

    @Override
    public List<CarteiraProporcaoResponse> buscarProporcaoCarteira(Long carteiraId) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        var ativos = carteira.getAtivos().stream()
                .map(ativo -> criarAtivoInformacaoResponse(ativo.getCodigo()))
                .toList();
        var valorTotal = somarValorAtivos(ativos);
        var ativosPorTipo = ativos.stream().collect(Collectors.groupingBy(AtivoInformacaoResponse::tipo));
        var carteiraProporcao = new ArrayList<CarteiraProporcaoResponse>();
        for (var entry: ativosPorTipo.entrySet()) {
            var valor = somarValorAtivos(entry.getValue());
            var proporcao = valor.divide(valorTotal, 5, RoundingMode.HALF_UP);
            carteiraProporcao.add(new CarteiraProporcaoResponse(entry.getKey(), valor, proporcao));
        }
        return carteiraProporcao;
    }

    private static BigDecimal somarValorAtivos(List<AtivoInformacaoResponse> ativos) {
        return ativos.stream()
                .map(ativo -> ativo.atual().valorTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
