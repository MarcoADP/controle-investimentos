package com.github.marcoadp.controle_investimentos.stub;

import com.github.marcoadp.controle_investimentos.dto.request.MovimentacaoRequest;
import com.github.marcoadp.controle_investimentos.dto.response.MovimentacaoResponse;
import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import com.github.marcoadp.controle_investimentos.enums.TipoAtivoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimentacaoStub {

    public static Movimentacao getMovimentacao() {
        return Movimentacao.builder()
                .data(LocalDate.of(2025,1, 1))
                .operacao(OperacaoEnum.ENTRADA)
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO)
                .quantidade(BigDecimal.TEN)
                .valorUnitario(BigDecimal.ONE)
                .valorTotal(BigDecimal.TEN)
                .build();
    }

    public static MovimentacaoRequest getMovimentacaoRequest() {
        return MovimentacaoRequest.builder()
                .data(LocalDate.of(2025,1, 1))
                .operacao(OperacaoEnum.ENTRADA)
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO.getDescricao())
                .quantidade(BigDecimal.TEN)
                .valorUnitario(BigDecimal.ONE)
                .build();
    }

    public static MovimentacaoResponse getMovimentacaoResponse() {
        return MovimentacaoResponse.builder()
                .data(LocalDate.of(2025,1, 1))
                .operacao(OperacaoEnum.ENTRADA)
                .codigo("ACAO4")
                .tipoAtivo(TipoAtivoEnum.ACAO.getDescricao())
                .quantidade(BigDecimal.TEN)
                .valorUnitario(BigDecimal.ONE)
                .valorTotal(BigDecimal.TEN)
                .build();
    }

    public static final String MOVIMENTACAO_REQUEST_JSON = """
            {
                "nome": "Movimentacao",
                "descricao": "Movimentacao de Ativos",
                "ativos": [
                    {
                        "codigo": "ACAO4"
                    }
                ]
            }";
    """;

}
