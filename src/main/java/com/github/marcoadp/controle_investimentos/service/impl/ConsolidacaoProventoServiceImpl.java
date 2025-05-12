package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.repository.ConsolidacaoProventoRepository;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoProventoService;
import com.github.marcoadp.controle_investimentos.service.ProventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ConsolidacaoProventoServiceImpl implements ConsolidacaoProventoService {

    private final ConsolidacaoProventoRepository consolidacaoRepository;

    private final CarteiraService carteiraService;

    private final ProventoService proventoService;

    @Override
    public List<ConsolidacaoProvento> consolidarCarteiras() {
        var carteiras = carteiraService.listar();
        List<ConsolidacaoProvento> consolidacoes = new ArrayList<>();
        carteiras.forEach(carteira ->  consolidacoes.addAll(consolidarCarteira(carteira)));
        return consolidacoes;
    }

    private List<ConsolidacaoProvento> consolidarCarteira(Carteira carteira) {
        return carteira.getAtivos().stream()
                .map(ativo -> consolidarAtivoPorAno(ativo.getCodigo()))
                .flatMap(List::stream)
                .toList();
    }

    private List<ConsolidacaoProvento> consolidarAtivoPorAno(String codigo) {
        var anoInicial = 2019;
        var anoFinal = LocalDate.now().getYear();
        List<ConsolidacaoProvento> consolidacoes = new ArrayList<>();
        for (int ano = anoInicial; ano <= anoFinal; ano++) {
            var consolidacaoOpt = consolidacaoRepository.findFirstByCodigoAndAno(codigo, ano);
            var id = consolidacaoOpt.map(ConsolidacaoProvento::getId).orElse(null);
            var consolidacao = ConsolidacaoProvento.builder()
                    .id(id)
                    .ano(ano)
                    .codigo(codigo)
                    .valorTotal(BigDecimal.ZERO)
                    .valorMedio(BigDecimal.ZERO)
                    .build();
            calcularValores(consolidacao, ano);
            consolidacoes.add(consolidacaoRepository.save(consolidacao));
        }

        return consolidacoes;
    }

    private void calcularValores(ConsolidacaoProvento consolidacao, int ano) {
        var proventos = proventoService.buscarPeloCodigo(consolidacao.getCodigo());
        if (proventos.isEmpty()) {
            return;
        }
        BigDecimal valorTotal = agregar(proventos, ano, Provento::getValorTotal);
        BigDecimal valorMedio = agregar(proventos, ano, Provento::getValorMedio);
        consolidacao.inserirValores(valorTotal, valorMedio);
    }

    public static BigDecimal agregar(List<Provento> proventos, int ano, Function<Provento, BigDecimal> campo) {
        return proventos.stream()
                .filter(provento -> provento.getDataPagamento().getYear() == ano)
                .map(campo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public ConsolidacaoProvento buscarPeloId(Long id) {
        return consolidacaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Movimentação", id));
    }

    @Override
    public List<ConsolidacaoProvento> buscarPeloCodigo(String codigo) {
        return consolidacaoRepository.findByCodigo(codigo);
    }

    @Override
    public ConsolidacaoProvento buscarPeloCodigoEAno(String codigo, int ano) {
        return consolidacaoRepository.findFirstByCodigoAndAno(codigo, ano).orElseThrow(() -> new NotFoundException("Consolidação", codigo));
    }

    @Override
    public void remover(Long id) {
        consolidacaoRepository.deleteById(id);
    }
}
