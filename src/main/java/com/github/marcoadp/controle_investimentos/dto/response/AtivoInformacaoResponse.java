package com.github.marcoadp.controle_investimentos.dto.response;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
public record AtivoInformacaoResponse(String codigo,
                                      String tipo,
                                      ValorInformacaoResponse entrada,
                                      ValorInformacaoResponse saida,
                                      ValorInformacaoResponse atual,
                                      BigDecimal saldo,
                                      BigDecimal variacao,
                                      BigDecimal rentabilidade ) {

    public static AtivoInformacaoResponse criar(Consolidacao consolidacao) {
        var entrada = new ValorInformacaoResponse(consolidacao.getQuantidadeEntrada(),
                consolidacao.getValorMedioEntrada(), consolidacao.getValorTotalEntrada());
        var saida = new ValorInformacaoResponse(consolidacao.getQuantidadeSaida(),
                consolidacao.getValorMedioSaida(), consolidacao.getValorTotalSaida());
        var atual = new ValorInformacaoResponse(entrada.quantidade().subtract(saida.quantidade()),
                BigDecimal.TEN, entrada.quantidade().multiply(BigDecimal.TEN));
        var saldo = atual.valorTotal().add(saida.valorTotal()).subtract(entrada.valorTotal());
        var variacao = saldo.divide(entrada.valorTotal(), 5, RoundingMode.HALF_UP);
        return AtivoInformacaoResponse.builder()
                .codigo(consolidacao.getCodigo())
                .tipo(consolidacao.getTipoAtivo().getDescricao())
                .entrada(entrada)
                .saida(saida)
                .atual(atual)
                .saldo(saldo)
                .variacao(variacao)
                .rentabilidade(variacao)
                .build();
    }

}
