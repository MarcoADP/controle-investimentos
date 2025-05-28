package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsolidacaoRepository extends JpaRepository<Consolidacao, Long> {

    Optional<Consolidacao> findFirstByCodigo(String codigo);

    Optional<Consolidacao> findFirstByCodigoAndMesAndAno(String codigo, int mes, int ano);

    List<Consolidacao> findByMesAndAno(Integer mes, Integer ano);
}
