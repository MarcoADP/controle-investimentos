package com.github.marcoadp.controle_investimentos.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public record DadosResumoResponse(BigDecimal valorInvestido,
                                  BigDecimal valorAtual,
                                  BigDecimal proventos,
                                  BigDecimal proventosAno,
                                  BigDecimal saldo,
                                  BigDecimal variacao,
                                  BigDecimal rentabilidade) {
    
    public static DadosResumoResponse calcular(List<AtivoInformacaoResponse> ativos, List<ProventoPeriodoResponse> proventosPorAno) {
        var valorInvestido = ativos.stream().map(ativo -> ativo.entrada().valorTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var valorAtual = ativos.stream().map(ativo -> ativo.atual().valorTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var proventoTotal = proventosPorAno.stream()
                .map(ProventoPeriodoResponse::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var ano = String.valueOf(LocalDate.now().getYear());
        var proventoAnoAtual = proventosPorAno.stream()
                .filter(proventoPeriodoResponse -> proventoPeriodoResponse.periodo().equals(ano))
                .map(ProventoPeriodoResponse::valor).findFirst().orElse(BigDecimal.ZERO);
        var saldo = valorAtual.subtract(valorInvestido);
        var variacao = saldo.divide(valorInvestido, 5, RoundingMode.HALF_UP);
        var rentabilidade = saldo.add(proventoTotal).divide(valorInvestido, 5, RoundingMode.HALF_UP);
        return new DadosResumoResponse(valorInvestido, valorAtual, proventoTotal, proventoAnoAtual, saldo, variacao, rentabilidade);
    }
    
}
