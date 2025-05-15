package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.dto.response.ConsolidacaoProventoAnualResponse;
import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import com.github.marcoadp.controle_investimentos.handler.NotFoundException;
import com.github.marcoadp.controle_investimentos.repository.ConsolidacaoProventoRepository;
import com.github.marcoadp.controle_investimentos.service.CarteiraService;
import com.github.marcoadp.controle_investimentos.service.ConsolidacaoProventoService;
import com.github.marcoadp.controle_investimentos.service.ProventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
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
        return consolidacaoRepository.saveAll(consolidacoes);
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
            for (var mes: Month.values()) {
                consolidarAtivoPorMes(codigo, mes, ano, consolidacoes);
            }
        }

        return consolidacoes;
    }

    private void consolidarAtivoPorMes(String codigo, Month mes, int ano, List<ConsolidacaoProvento> consolidacoes) {
        for (TipoProventoEnum tipoProvento: TipoProventoEnum.values()) {
            var consolidacaoOpt = consolidacaoRepository.findFirstByCodigoAndMesAndAnoAndTipoProvento(codigo, mes.getValue(), ano, tipoProvento);
            var id = consolidacaoOpt.map(ConsolidacaoProvento::getId).orElse(null);
            var consolidacao = ConsolidacaoProvento.builder()
                    .id(id)
                    .mes(mes.getValue())
                    .ano(ano)
                    .codigo(codigo)
                    .tipoProvento(tipoProvento)
                    .valorTotal(BigDecimal.ZERO)
                    .valorMedio(BigDecimal.ZERO)
                    .build();
            calcularValores(consolidacao, ano, tipoProvento);
            if (!consolidacao.getValorTotal().equals(BigDecimal.ZERO)) {
                consolidacoes.add(consolidacao);
            }
        }
    }

    private void calcularValores(ConsolidacaoProvento consolidacao, int ano, TipoProventoEnum tipoProvento) {
        var proventos = proventoService.buscarPeloCodigo(consolidacao.getCodigo());
        if (proventos.isEmpty()) {
            return;
        }
        BigDecimal valorTotal = agregar(proventos, ano, tipoProvento, Provento::getValorTotal);
        BigDecimal valorMedio = agregar(proventos, ano, tipoProvento, Provento::getValorMedio);
        consolidacao.inserirValores(valorTotal, valorMedio);
    }

    public static BigDecimal agregar(List<Provento> proventos,
                                     int ano,
                                     TipoProventoEnum tipoProvento,
                                     Function<Provento, BigDecimal> campo
    ) {
        return proventos.stream()
                .filter(provento -> provento.getDataPagamento().getYear() == ano && provento.getTipoProvento().equals(tipoProvento))
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
    public List<ConsolidacaoProvento> buscarPeloCodigoEAno(String codigo, int ano) {
        return consolidacaoRepository.findByCodigoAndAno(codigo, ano);
    }

    @Override
    public void remover(Long id) {
        consolidacaoRepository.deleteById(id);
    }

    @Override
    public ConsolidacaoProventoAnualResponse buscarConsolidacaoAnualPeloCodigo(String codigo, int ano) {
        List<ConsolidacaoProvento> consolidacoes = consolidacaoRepository.findByCodigoAndAno(codigo, ano);
        return ConsolidacaoProventoAnualResponse.builder()
                .codigo(codigo)
                .ano(ano)
                .valorTotal(consolidacoes.stream().map(ConsolidacaoProvento::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add))
                .valorMedio(consolidacoes.stream().map(ConsolidacaoProvento::getValorMedio).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}
