package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Movimentacao;
import com.github.marcoadp.controle_investimentos.enums.OperacaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByCodigo(String codigo);

    List<Movimentacao> findByCodigoAndDataLessThanEqual(String codigo, LocalDate data);

    List<Movimentacao> findByOperacao(OperacaoEnum operacaoEnum);
}
