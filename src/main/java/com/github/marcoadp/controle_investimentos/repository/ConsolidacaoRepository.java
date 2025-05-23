package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Consolidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsolidacaoRepository extends JpaRepository<Consolidacao, Long> {

    Optional<Consolidacao> findFirstByCodigo(String codigo);

}
