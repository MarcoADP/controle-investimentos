package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Acao;
import com.github.marcoadp.controle_investimentos.mapper.AcaoMapper;
import com.github.marcoadp.controle_investimentos.repository.AcaoRepository;
import com.github.marcoadp.controle_investimentos.service.SetorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.AcaoStub.getAcao;
import static com.github.marcoadp.controle_investimentos.stub.AcaoStub.getAcaoRequest;
import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcaoServiceImplTest {

    @Mock
    private AcaoRepository acaoRepository;

    @Mock
    private AcaoMapper acaoMapper;

    @Mock
    private SetorService setorService;

    @InjectMocks
    private AcaoServiceImpl acaoService;

    @Test
    void criar() {

        var request = getAcaoRequest();
        var acaoMock = getAcao();
        when(setorService.buscarPeloId(request.setorId())).thenReturn(getSetor());
        when(acaoMapper.toAcao(request)).thenReturn(acaoMock);
        when(acaoRepository.save(acaoMock)).thenReturn(acaoMock);

        var acao = acaoService.criar(request);
        assertAcao(acao, acaoMock);

    }

    @Test
    void atualizar() {

        var request = getAcaoRequest();
        var acaoMock = getAcao();
        when(setorService.buscarPeloId(request.setorId())).thenReturn(getSetor());
        when(acaoRepository.findById(1L)).thenReturn(Optional.of(acaoMock));
        when(acaoRepository.save(acaoMock)).thenReturn(acaoMock);

        var acao = acaoService.atualizar(acaoMock.getId(), request);
        assertAcao(acao, acaoMock);
    }

    @Test
    void buscarPeloId() {
        var acaoMock = getAcao();
        when(acaoRepository.findById(1L)).thenReturn(Optional.of(acaoMock));
        var acao = acaoService.buscarPeloId(1L);
        assertAcao(acao, acaoMock);
    }

    @Test
    void buscarPeloCodigo() {
        var acaoMock = getAcao();
        when(acaoRepository.findFirstByCodigo(acaoMock.getCodigo())).thenReturn(Optional.of(acaoMock));
        var acao = acaoService.buscarPeloCodigo("ACAO4");
        assertAcao(acao.get(), acaoMock);
    }

    @Test
    void buscarPeloSetor() {
        var acaoMock = getAcao();
        when(acaoRepository.findBySetor(1L)).thenReturn(List.of(acaoMock));
        var acoes = acaoService.buscarPeloSetor(1L);
        assertAcao(acoes.getFirst(), acaoMock);
    }

    @Test
    void listar() {
        var acaoMock = getAcao();
        when(acaoRepository.findAll()).thenReturn(List.of(acaoMock));
        var acoes = acaoService.listar();
        assertThat(acoes).hasSize(1);
        assertAcao(acoes.getFirst(), acaoMock);
    }

    private static void assertAcao(Acao acao, Acao acaoMock) {
        assertThat(acao).isNotNull();
        assertThat(acao.getId()).isEqualTo(acaoMock.getId());
        assertThat(acao.getNome()).isEqualTo(acaoMock.getNome());
        assertThat(acao.getCodigo()).isEqualTo(acaoMock.getCodigo());
        assertThat(acao.getCnpj()).isEqualTo(acaoMock.getCnpj());
        assertThat(acao.getSetor().getNome()).isEqualTo(acaoMock.getSetor().getNome());
    }
}