package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Etf;
import com.github.marcoadp.controle_investimentos.mapper.EtfMapper;
import com.github.marcoadp.controle_investimentos.repository.EtfRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.EtfStub.getEtf;
import static com.github.marcoadp.controle_investimentos.stub.EtfStub.getEtfRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtfServiceImplTest {

    @Mock
    private EtfRepository etfRepository;

    @Mock
    private EtfMapper etfMapper;

    @InjectMocks
    private EtfServiceImpl etfService;

    @Test
    void criar() {

        var request = getEtfRequest();
        var etfMock = getEtf(null);
        when(etfMapper.toEtf(request)).thenReturn(etfMock);
        when(etfRepository.save(etfMock)).thenReturn(etfMock);

        var etf = etfService.criar(request);
        assertEtf(etf, etfMock);

    }

    @Test
    void atualizar() {
        var request = getEtfRequest();
        var etfMock = getEtf(1L);
        when(etfRepository.findById(1L)).thenReturn(Optional.of(etfMock));
        when(etfRepository.save(etfMock)).thenReturn(etfMock);

        var etf = etfService.atualizar(etfMock.getId(), request);
        assertEtf(etf, etfMock);
    }

    @Test
    void buscarPeloId() {
        var etfMock = getEtf(1L);
        when(etfRepository.findById(1L)).thenReturn(Optional.of(etfMock));
        var etf = etfService.buscarPeloId(1L);
        assertEtf(etf, etfMock);
    }

    @Test
    void buscarPeloCodigo() {
        var etfMock = getEtf(1L);
        when(etfRepository.findFirstByCodigo(etfMock.getCodigo())).thenReturn(Optional.of(etfMock));
        var etf = etfService.buscarPeloCodigo("ETF11");
        assertEtf(etf.get(), etfMock);
    }

    @Test
    void buscarPeloSetor() {
        var etfMock = getEtf(1L);
        when(etfRepository.findByTipo("Tipo")).thenReturn(List.of(etfMock));
        var etfs = etfService.buscarPeloTipo("Tipo");
        assertEtf(etfs.getFirst(), etfMock);
    }

    @Test
    void listar() {
        var etfMock = getEtf(1L);
        when(etfRepository.findAll()).thenReturn(List.of(etfMock));
        var etfs = etfService.listar();
        assertThat(etfs).hasSize(1);
        assertEtf(etfs.getFirst(), etfMock);
    }

    private static void assertEtf(Etf etf, Etf etfMock) {
        assertThat(etf).isNotNull();
        assertThat(etf.getId()).isEqualTo(etfMock.getId());
        assertThat(etf.getNome()).isEqualTo(etfMock.getNome());
        assertThat(etf.getCodigo()).isEqualTo(etfMock.getCodigo());
        assertThat(etf.getCnpj()).isEqualTo(etfMock.getCnpj());
        assertThat(etf.getTipo()).isEqualTo(etfMock.getTipo());
    }
}