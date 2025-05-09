package com.github.marcoadp.controle_investimentos.service.impl;

import com.github.marcoadp.controle_investimentos.entity.Setor;
import com.github.marcoadp.controle_investimentos.mapper.SetorMapper;
import com.github.marcoadp.controle_investimentos.repository.SetorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetor;
import static com.github.marcoadp.controle_investimentos.stub.SetorStub.getSetorRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SetorServiceImplTest {

    @Mock
    private SetorRepository setorRepository;

    @Mock
    private SetorMapper setorMapper;

    @InjectMocks
    private SetorServiceImpl setorService;

    @Test
    void criar() {

        var request = getSetorRequest();
        var setorMock = getSetor();
        when(setorMapper.toSetor(request)).thenReturn(setorMock);
        when(setorRepository.save(setorMock)).thenReturn(setorMock);

        var setor = setorService.criar(request);
        assertSetor(setor, setorMock);

    }

    @Test
    void atualizar() {

        var request = getSetorRequest();
        var setorMock = getSetor();
        when(setorRepository.findById(1L)).thenReturn(Optional.of(setorMock));
        when(setorRepository.save(setorMock)).thenReturn(setorMock);

        var setor = setorService.atualizar(setorMock.getId(), request);
        assertSetor(setor, setorMock);
    }

    @Test
    void buscarPeloId() {
        var setorMock = getSetor();
        when(setorRepository.findById(1L)).thenReturn(Optional.of(setorMock));
        var setor = setorService.buscarPeloId(1L);
        assertSetor(setor, setorMock);
    }

    @Test
    void listar() {
        var setorMock = getSetor();
        when(setorRepository.findAll()).thenReturn(List.of(setorMock));
        var setores = setorService.listar();
        assertThat(setores).hasSize(1);
        assertSetor(setores.get(0), setorMock);
    }

    private static void assertSetor(Setor setor, Setor setorMock) {
        assertThat(setor).isNotNull();
        assertThat(setor.getId()).isEqualTo(setorMock.getId());
        assertThat(setor.getNome()).isEqualTo(setorMock.getNome());
    }
}