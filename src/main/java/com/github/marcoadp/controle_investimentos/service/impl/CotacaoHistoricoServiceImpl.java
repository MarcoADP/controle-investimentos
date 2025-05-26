package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.client.BrapiClient;
import com.github.marcoadp.controle_investimentos.config.BrapiProperties;
import com.github.marcoadp.controle_investimentos.dto.request.CotacaoHistoricoRequest;
import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.repository.CotacaoHistoricoRepository;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.CotacaoHistoricoService;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class CotacaoHistoricoServiceImpl implements CotacaoHistoricoService {

    private final CotacaoHistoricoRepository cotacaoHistoricoRepository;

    private final CarteiraService carteiraService;

    private final MovimentacaoService movimentacaoService;

    private final BrapiClient brapiClient;

    private final BrapiProperties brapiProperties;

    @Override
    public List<CotacaoHistorico> gerarCotacoesManual(List<CotacaoHistoricoRequest> requests) {
        var cotacoes = new ArrayList<CotacaoHistorico>();
        for (CotacaoHistoricoRequest request: requests) {
            log.info("Gerando para {} em {} com R$ {}", request.codigo(), request.data(), request.valor());
            var cotacaoOpt = cotacaoHistoricoRepository.findByCodigoAndData(request.codigo(), request.data());
            if (cotacaoOpt.isPresent()) {
                cotacoes.add(cotacaoOpt.get());
            } else {
                cotacoes.add(criarCotacao(request.codigo(), request.data(), request.valor()));
            }
        }
        return cotacoes;
    }

    @Override
    public List<CotacaoHistorico> gerarCotacoesDia(Long carteiraId) {
        var carteira = carteiraService.buscarPeloId(carteiraId);
        return carteira.getAtivos().stream()
                .map(ativo -> gerarCotacaoPeloCodigo(ativo.getCodigo(), LocalDate.now()))
                .toList();
    }

    private CotacaoHistorico gerarCotacaoPeloCodigo(String codigo, LocalDate data) {
        log.info("Gerar cotação para #{} em {}", codigo, data);
        var cotacaoOpt = cotacaoHistoricoRepository.findByCodigoAndData(codigo, data);
        if (cotacaoOpt.isPresent()) {
            return cotacaoOpt.get();
        }

        try {
            var cotacaoBrapi = brapiClient.buscarPorCotacao(codigo, brapiProperties.getToken());
            var cotacao = criarCotacao(codigo, data, cotacaoBrapi.results().getFirst().regularMarketPrice());
            return cotacaoHistoricoRepository.save(cotacao);
        } catch (Exception e) {
            return CotacaoHistorico.builder().build();
        }

    }

    private CotacaoHistorico criarCotacao(String codigo, LocalDate data, BigDecimal valor) {
        var movimentacoes = movimentacaoService.buscarPeloCodigoEData(codigo, data);
        var quantidadeEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getQuantidade);
        var valorTotalEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getValorTotal);
        var quantidadeSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getQuantidade);
        var valorMedioEntrada = quantidadeEntrada.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : valorTotalEntrada.divide(quantidadeEntrada, 2, RoundingMode.HALF_UP);
        var quantidade = quantidadeEntrada.subtract(quantidadeSaida).intValue();
        if (quantidade == 0) {
            return CotacaoHistorico.builder().build();
        }
        var cotacao = new CotacaoHistorico(codigo, data);
        cotacao.calcular(quantidade, valor, valorMedioEntrada);
        return cotacaoHistoricoRepository.save(cotacao);

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
    public CotacaoHistorico buscarPeloId(Long id) {
        return cotacaoHistoricoRepository.findById(id).orElseThrow(() -> new NotFoundException("Movimentação", id));
    }

    @Override
    public List<CotacaoHistorico> buscarPeloCodigo(String codigo) {
        return cotacaoHistoricoRepository.findByCodigo(codigo).stream()
                .sorted(Comparator.comparing(CotacaoHistorico::getData)).toList();
    }

    @Override
    public List<CotacaoHistorico> buscarPelaData(LocalDate data) {
        return cotacaoHistoricoRepository.findByData(data);
    }

    @Override
    public Optional<CotacaoHistorico> buscarCotacaoMaisRecente(String codigo) {
        List<CotacaoHistorico> cotacoes = cotacaoHistoricoRepository.findByCodigo(codigo);
        return cotacoes.stream().max(Comparator.comparing(CotacaoHistorico::getData));

    }

    @Override
    public void remover(Long id) {
        cotacaoHistoricoRepository.deleteById(id);
    }

    @Override
    public List<CotacaoHistorico> buscarCotacaoMaisProxima(LocalDate data) {
        var cotacoes = cotacaoHistoricoRepository.findMaisProximaPorData(data);
        if (cotacoes.isEmpty()) {
            return List.of();
        }
        var dataAlvo = cotacoes.getFirst().getData();
        return cotacoes.stream()
                .filter(cotacaoHistorico -> cotacaoHistorico.getData().equals(dataAlvo))
                .toList();
    }
}
