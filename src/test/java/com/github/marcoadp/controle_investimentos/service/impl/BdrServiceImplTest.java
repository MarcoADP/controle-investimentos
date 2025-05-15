package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Bdr;
import com.github.marcoadp.controle_investimentos.mapper.BdrMapper;
import com.github.marcoadp.controle_investimentos.repository.BdrRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.BdrStub.getBdr;
import static com.github.marcoadp.controle_investimentos.stub.BdrStub.getBdrRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BdrServiceImplTest {

    @Mock
    private BdrRepository bdrRepository;

    @Mock
    private BdrMapper bdrMapper;

    @InjectMocks
    private BdrServiceImpl bdrService;

    @Test
    void criar() {

        var request = getBdrRequest();
        var bdrMock = getBdr(null);
        when(bdrMapper.toBdr(request)).thenReturn(bdrMock);
        when(bdrRepository.save(bdrMock)).thenReturn(bdrMock);

        var bdr = bdrService.criar(request);
        assertBdr(bdr, bdrMock);

    }

    @Test
    void atualizar() {
        var request = getBdrRequest();
        var bdrMock = getBdr(1L);
        when(bdrRepository.findById(1L)).thenReturn(Optional.of(bdrMock));
        when(bdrRepository.save(bdrMock)).thenReturn(bdrMock);

        var bdr = bdrService.atualizar(bdrMock.getId(), request);
        assertBdr(bdr, bdrMock);
    }

    @Test
    void buscarPeloId() {
        var bdrMock = getBdr(1L);
        when(bdrRepository.findById(1L)).thenReturn(Optional.of(bdrMock));
        var bdr = bdrService.buscarPeloId(1L);
        assertBdr(bdr, bdrMock);
    }

    @Test
    void buscarPeloCodigo() {
        var bdrMock = getBdr(1L);
        when(bdrRepository.findFirstByCodigo(bdrMock.getCodigo())).thenReturn(Optional.of(bdrMock));
        var bdr = bdrService.buscarPeloCodigo("BDRZ32");
        assertBdr(bdr.get(), bdrMock);
    }

    @Test
    void buscarPeloSetor() {
        var bdrMock = getBdr(1L);
        when(bdrRepository.findBySetor("Setor")).thenReturn(List.of(bdrMock));
        var bdrs = bdrService.buscarPeloSetor("Setor");
        assertBdr(bdrs.getFirst(), bdrMock);
    }

    @Test
    void listar() {
        var bdrMock = getBdr(1L);
        when(bdrRepository.findAll()).thenReturn(List.of(bdrMock));
        var bdrs = bdrService.listar();
        assertThat(bdrs).hasSize(1);
        assertBdr(bdrs.getFirst(), bdrMock);
    }

    private static void assertBdr(Bdr bdr, Bdr bdrMock) {
        assertThat(bdr).isNotNull();
        assertThat(bdr.getId()).isEqualTo(bdrMock.getId());
        assertThat(bdr.getNome()).isEqualTo(bdrMock.getNome());
        assertThat(bdr.getCodigo()).isEqualTo(bdrMock.getCodigo());
        assertThat(bdr.getCnpj()).isEqualTo(bdrMock.getCnpj());
        assertThat(bdr.getSetor()).isEqualTo(bdrMock.getSetor());
    }
}