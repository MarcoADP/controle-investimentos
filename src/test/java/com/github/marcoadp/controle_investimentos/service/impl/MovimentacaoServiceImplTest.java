package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.mapper.MovimentacaoMapper;
import com.github.marcoadp.controle_investimentos.repository.MovimentacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.MovimentacaoStub.getMovimentacao;
import static com.github.marcoadp.controle_investimentos.stub.MovimentacaoStub.getMovimentacaoRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimentacaoServiceImplTest {

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private MovimentacaoMapper movimentacaoMapper;

    @InjectMocks
    private MovimentacaoServiceImpl movimentacaoService;

    @Test
    void criar() {

        var request = getMovimentacaoRequest();
        var movimentacaoMock = getMovimentacao();
        when(movimentacaoMapper.toMovimentacao(request)).thenReturn(movimentacaoMock);
        when(movimentacaoRepository.save(movimentacaoMock)).thenReturn(movimentacaoMock);

        var movimentacao = movimentacaoService.criar(request);
        assertThat(movimentacao).usingRecursiveComparison().isEqualTo(movimentacaoMock);

    }

    @Test
    void buscarPeloId() {
        var movimentacaoMock = getMovimentacao();
        when(movimentacaoRepository.findById(1L)).thenReturn(Optional.of(movimentacaoMock));
        var movimentacao = movimentacaoService.buscarPeloId(1L);
        assertThat(movimentacao).usingRecursiveComparison().isEqualTo(movimentacaoMock);
    }

    @Test
    void buscarPeloCodigo() {
        var movimentacaoMock = getMovimentacao();
        when(movimentacaoRepository.findByCodigo("ACAO4")).thenReturn(List.of(movimentacaoMock));
        var movimentacoes = movimentacaoService.buscarPeloCodigo("ACAO4");
        assertThat(movimentacoes).hasSize(1);
        assertThat(movimentacoes.getFirst()).usingRecursiveComparison().isEqualTo(movimentacaoMock);
    }
}