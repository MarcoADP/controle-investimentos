package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Carteira;
import com.github.marcoadp.controle_investimentos.mapper.CarteiraMapper;
import com.github.marcoadp.controle_investimentos.repository.CarteiraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.CarteiraStub.getCarteira;
import static com.github.marcoadp.controle_investimentos.stub.CarteiraStub.getCarteiraRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarteiraServiceImplTest {

    @Mock
    private CarteiraRepository carteiraRepository;

    @Mock
    private CarteiraMapper carteiraMapper;

    @InjectMocks
    private CarteiraServiceImpl carteiraService;

    @Test
    void criar() {

        var request = getCarteiraRequest();
        var carteiraMock = getCarteira();
        when(carteiraMapper.toCarteira(request)).thenReturn(carteiraMock);
        when(carteiraRepository.save(carteiraMock)).thenReturn(carteiraMock);

        var carteira = carteiraService.criar(request);
        assertCarteira(carteira, carteiraMock);

    }

    @Test
    void buscarPeloId() {
        var carteiraMock = getCarteira();
        when(carteiraRepository.findById(1L)).thenReturn(Optional.of(carteiraMock));
        var carteira = carteiraService.buscarPeloId(1L);
        assertCarteira(carteira, carteiraMock);
    }

    private static void assertCarteira(Carteira carteira, Carteira carteiraMock) {
        assertThat(carteira).isNotNull();
        assertThat(carteira.getId()).isEqualTo(carteiraMock.getId());
        assertThat(carteira.getNome()).isEqualTo(carteiraMock.getNome());
        assertThat(carteira.getDescricao()).isEqualTo(carteiraMock.getDescricao());
        assertThat(carteira.getAtivos()).hasSize(carteiraMock.getAtivos().size());
        assertThat(carteira.getAtivos().getFirst().getCodigo()).isEqualTo(carteiraMock.getAtivos().getFirst().getCodigo());
    }
}