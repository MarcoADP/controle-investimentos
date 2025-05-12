package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.ConsolidacaoProvento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsolidacaoProventoRepository extends JpaRepository<ConsolidacaoProvento, Long> {

    List<ConsolidacaoProvento> findByCodigo(String codigo);

    Optional<ConsolidacaoProvento> findFirstByCodigoAndAnoAndTipoProvento(String codigo, Integer ano, TipoProventoEnum tipoProvento);

    List<ConsolidacaoProvento> findByCodigoAndAno(String codigo, int ano);
}
