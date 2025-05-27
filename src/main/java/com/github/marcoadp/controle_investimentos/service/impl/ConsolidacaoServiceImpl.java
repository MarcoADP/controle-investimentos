package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.repository.ConsolidacaoRepository;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoService;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsolidacaoServiceImpl implements ConsolidacaoService {

    private final ConsolidacaoRepository consolidacaoRepository;

    private final MovimentacaoService movimentacaoService;

    @Override
    public List<Consolidacao> consolidarTodasMovimentacoes() {
        var movimentacoes = movimentacaoService.buscarTodas();
        var codigos = movimentacoes.stream().map(Movimentacao::getCodigo).collect(Collectors.toSet());
        return codigos.stream().map(this::consolidarAtivoPorAno).flatMap(List::stream).toList();
    }

    @Override
    public List<Consolidacao> consolidarAtivoPorData(Integer mes, Integer ano) {
        var movimentacoes = movimentacaoService.buscarTodas();
        var codigos = movimentacoes.stream().map(Movimentacao::getCodigo).collect(Collectors.toSet());
        return codigos.stream()
                .map(codigo -> consolidarAtivo(codigo, Month.of(mes), ano, movimentacaoService.buscarPeloCodigo(codigo)))
                .toList();
    }

    @Override
    public List<Consolidacao> consolidarAtivoPorAno(String codigo) {
        var anoInicial = 2019;
        var anoFinal = LocalDate.now().getYear();
        var movimentacoes = movimentacaoService.buscarPeloCodigo(codigo);
        var consolidacoes = new ArrayList<Consolidacao>();
        var hoje = LocalDate.now().plusMonths(1L);
        for (int ano = anoInicial; ano <= anoFinal; ano++) {
            for (var mes: Month.values()) {
                var dataRef = LocalDate.now().withMonth(mes.getValue()).withYear(ano).with(TemporalAdjusters.lastDayOfMonth());
                if (dataRef.isAfter(hoje)) {
                    break;
                }
                consolidacoes.add(consolidarAtivo(codigo, mes, ano, movimentacoes));
            }
        }
        return consolidacoes;
    }

    private Consolidacao consolidarAtivo(String codigo, Month mes, int ano, List<Movimentacao> movimentacoes) {
        log.info("{}/{} => {}", mes, ano, codigo);
        var consolidacaoOpt = consolidacaoRepository.findFirstByCodigoAndMesAndAno(codigo, mes.getValue(), ano);
        var id = consolidacaoOpt.map(Consolidacao::getId).orElse(null);
        var consolidacao = Consolidacao.builder()
                .id(id)
                .codigo(codigo)
                .mes(mes.getValue())
                .ano(ano)
                .build();
        var data = LocalDate.now().withMonth(mes.getValue()).withYear(ano).with(TemporalAdjusters.lastDayOfMonth());
        var movimentacoesFiltered = movimentacoes.stream()
                .filter(movimentacao -> !movimentacao.getData().isAfter(data))
                .toList();

        if (movimentacoesFiltered.isEmpty() || ativoRemovido(data, movimentacoesFiltered)) {
            return consolidacao;
        }
        calcularValores(consolidacao, movimentacoesFiltered);
        return consolidacaoRepository.save(consolidacao);
    }

    private boolean ativoRemovido(LocalDate data, List<Movimentacao> movimentacoes) {
        var dataInicio = data.with(TemporalAdjusters.firstDayOfMonth());
        var quantidadeEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getQuantidade);
        var quantidadeSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getQuantidade);
        var existeMovimentacaoNoMes = movimentacoes.stream()
                .anyMatch(movimentacao -> !movimentacao.getData().isBefore(dataInicio));
        return quantidadeEntrada.equals(quantidadeSaida) && !existeMovimentacaoNoMes;
    }

    private void calcularValores(Consolidacao consolidacao, List<Movimentacao> movimentacoes) {
        if (movimentacoes.isEmpty()) {
            return;
        }
        consolidacao.setTipoAtivo(movimentacoes.getFirst().getTipoAtivo());
        var quantidadeEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getQuantidade);
        var valorTotalEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getValorTotal);
        var quantidadeSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getQuantidade);
        var valorTotalSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getValorTotal);
        consolidacao.calcularEntrada(quantidadeEntrada, valorTotalEntrada);
        consolidacao.calcularSaida(quantidadeSaida, valorTotalSaida);
        consolidacao.calcularLucro();
    }

    public static BigDecimal agregar(List<Movimentacao> movimentacoes,
                                   OperacaoEnum operacao,
                                   Function<Movimentacao, BigDecimal> campo) {
        return movimentacoes.stream()
                .filter(m -> m.getOperacao().equals(operacao))
                .map(campo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Consolidacao buscarPeloId(Long id) {
        return consolidacaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Movimentação", id));
    }

    @Override
    public Consolidacao buscarPeloCodigo(String codigo) {
        return consolidacaoRepository.findFirstByCodigo(codigo).orElseThrow(() -> new NotFoundException("Movimentação", codigo));
    }

    @Override
    public void remover(Long id) {
        consolidacaoRepository.deleteById(id);
    }
}
