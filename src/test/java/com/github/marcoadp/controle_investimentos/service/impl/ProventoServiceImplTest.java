package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import com.github.marcoadp.controle_investimentos.mapper.ProventoMapper;
import com.github.marcoadp.controle_investimentos.repository.ProventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.ProventoStub.getProvento;
import static com.github.marcoadp.controle_investimentos.stub.ProventoStub.getProventoRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProventoServiceImplTest {

    @Mock
    private ProventoRepository proventoRepository;

    @Mock
    private ProventoMapper proventoMapper;

    @InjectMocks
    private ProventoServiceImpl proventoService;

    @Test
    void criar() {

        var request = getProventoRequest();
        var proventoMock = getProvento();
        when(proventoMapper.toProvento(request)).thenReturn(proventoMock);
        when(proventoRepository.save(proventoMock)).thenReturn(proventoMock);

        var provento = proventoService.criar(request);
        assertThat(provento).usingRecursiveComparison().isEqualTo(proventoMock);

    }

    @Test
    void buscarPeloId() {
        var proventoMock = getProvento();
        when(proventoRepository.findById(1L)).thenReturn(Optional.of(proventoMock));
        var provento = proventoService.buscarPeloId(1L);
        assertThat(provento).usingRecursiveComparison().isEqualTo(proventoMock);
    }

    @Test
    void buscarPeloCodigo() {
        var proventoMock = getProvento();
        when(proventoRepository.findByCodigo("ACAO4")).thenReturn(List.of(proventoMock));
        var movimentacoes = proventoService.buscarPeloCodigo("ACAO4");
        assertThat(movimentacoes).hasSize(1);
        assertThat(movimentacoes.getFirst()).usingRecursiveComparison().isEqualTo(proventoMock);
    }

    @Test
    void buscarPeloTipoProvento() {
        var proventoMock = getProvento();
        when(proventoRepository.findByTipoProvento(TipoProventoEnum.DIVIDENDOS)).thenReturn(List.of(proventoMock));
        var movimentacoes = proventoService.buscarPeloTipoProvento("Dividendos");
        assertThat(movimentacoes).hasSize(1);
        assertThat(movimentacoes.getFirst()).usingRecursiveComparison().isEqualTo(proventoMock);
    }
}