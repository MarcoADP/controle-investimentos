package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import com.github.marcoadp.controle_investimentos.mapper.FundoImobiliarioMapper;
import com.github.marcoadp.controle_investimentos.repository.FundoImobiliarioRepository;
import com.github.marcoadp.controle_investimentos.repository.spec.FundoImobiliarioSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.FundoImobiliarioStub.getFundoImobiliario;
import static com.github.marcoadp.controle_investimentos.stub.FundoImobiliarioStub.getFundoImobiliarioRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FundoImobiliarioServiceImplTest {

    @Mock
    private FundoImobiliarioRepository fundoImobiliarioRepository;

    @Mock
    private FundoImobiliarioMapper fundoImobiliarioMapper;

    @InjectMocks
    private FundoImobiliarioServiceImpl fundoImobiliarioService;

    @Test
    void criar() {

        var request = getFundoImobiliarioRequest();
        var fundoImobiliarioMock = getFundoImobiliario();
        when(fundoImobiliarioMapper.toFundoImobiliario(request)).thenReturn(fundoImobiliarioMock);
        when(fundoImobiliarioRepository.save(fundoImobiliarioMock)).thenReturn(fundoImobiliarioMock);

        var fundoImobiliario = fundoImobiliarioService.criar(request);
        assertFundoImobiliario(fundoImobiliario, fundoImobiliarioMock);

    }

    @Test
    void atualizar() {
        var request = getFundoImobiliarioRequest();
        var fundoImobiliarioMock = getFundoImobiliario();
        when(fundoImobiliarioRepository.findById(1L)).thenReturn(Optional.of(fundoImobiliarioMock));
        when(fundoImobiliarioRepository.save(fundoImobiliarioMock)).thenReturn(fundoImobiliarioMock);

        var fundoImobiliario = fundoImobiliarioService.atualizar(fundoImobiliarioMock.getId(), request);
        assertFundoImobiliario(fundoImobiliario, fundoImobiliarioMock);
    }

    @Test
    void buscarPeloId() {
        var fundoImobiliarioMock = getFundoImobiliario();
        when(fundoImobiliarioRepository.findById(1L)).thenReturn(Optional.of(fundoImobiliarioMock));
        var fundoImobiliario = fundoImobiliarioService.buscarPeloId(1L);
        assertFundoImobiliario(fundoImobiliario, fundoImobiliarioMock);
    }

    @Test
    void buscarPeloCodigo() {
        var fundoImobiliarioMock = getFundoImobiliario();
        when(fundoImobiliarioRepository.findFirstByCodigo(fundoImobiliarioMock.getCodigo())).thenReturn(Optional.of(fundoImobiliarioMock));
        var fundoImobiliario = fundoImobiliarioService.buscarPeloCodigo("FIMO11");
        assertFundoImobiliario(fundoImobiliario, fundoImobiliarioMock);
    }

    @Test
    void buscar() {
        var fundoImobiliarioMock = getFundoImobiliario();
        when(fundoImobiliarioRepository.findAll(any(Specification.class))).thenReturn(List.of(fundoImobiliarioMock));
        var fundoImobiliarios = fundoImobiliarioService.buscar("FIMO11", null, null);
        assertFundoImobiliario(fundoImobiliarios.getFirst(), fundoImobiliarioMock);
    }

    private static void assertFundoImobiliario(FundoImobiliario fundoImobiliario, FundoImobiliario fundoImobiliarioMock) {
        assertThat(fundoImobiliario).isNotNull();
        assertThat(fundoImobiliario.getId()).isEqualTo(fundoImobiliarioMock.getId());
        assertThat(fundoImobiliario.getNome()).isEqualTo(fundoImobiliarioMock.getNome());
        assertThat(fundoImobiliario.getCodigo()).isEqualTo(fundoImobiliarioMock.getCodigo());
        assertThat(fundoImobiliario.getCnpj()).isEqualTo(fundoImobiliarioMock.getCnpj());
        assertThat(fundoImobiliario.getTipo()).isEqualTo(fundoImobiliarioMock.getTipo());
        assertThat(fundoImobiliario.getSegmento()).isEqualTo(fundoImobiliarioMock.getSegmento());
    }
}