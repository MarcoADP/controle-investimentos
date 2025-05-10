package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.repository.ConsolidacaoRepository;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoService;
import com.github.marcoadp.controle_investimentos.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ConsolidacaoServiceImpl implements ConsolidacaoService {

    private final ConsolidacaoRepository consolidacaoRepository;

    private final CarteiraService carteiraService;

    private final MovimentacaoService movimentacaoService;

    @Override
    public List<Consolidacao> consolidarCarteiras() {
        var carteiras = carteiraService.listar();
        List<Consolidacao> consolidacoes = new ArrayList<>();
        carteiras.forEach(carteira ->  consolidacoes.addAll(consolidarCarteira(carteira)));
        return consolidacoes;
    }

    private List<Consolidacao> consolidarCarteira(Carteira carteira) {
        return carteira.getAtivos().stream().map(ativo -> consolidarAtivo(ativo.getCodigo())).toList();
    }

    private Consolidacao consolidarAtivo(String codigo) {
        var consolidacaoOpt = consolidacaoRepository.findFirstByCodigo(codigo);
        var id = consolidacaoOpt.map(Consolidacao::getId).orElse(null);
        var consolidacao = Consolidacao.builder()
                .id(id)
                .codigo(codigo)
                .build();


        calcularValores(consolidacao);
        return consolidacaoRepository.save(consolidacao);
    }

    private void calcularValores(Consolidacao consolidacao) {
        var movimentacoes = movimentacaoService.buscarPeloCodigo(consolidacao.getCodigo());
        if (movimentacoes.isEmpty()) {
            return;
        }
        consolidacao.setTipoAtivo(movimentacoes.getFirst().getTipoAtivo());
        BigDecimal quantidadeEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getQuantidade);
        BigDecimal valorTotalEntrada = agregar(movimentacoes, OperacaoEnum.ENTRADA, Movimentacao::getValorTotal);
        BigDecimal quantidadeSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getQuantidade);
        BigDecimal valorTotalSaida = agregar(movimentacoes, OperacaoEnum.SAIDA, Movimentacao::getValorTotal);
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
