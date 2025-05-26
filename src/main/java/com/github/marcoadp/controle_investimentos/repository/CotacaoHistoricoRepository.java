package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.CotacaoHistorico;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CotacaoHistoricoRepository extends JpaRepository<CotacaoHistorico, Long> {

    List<CotacaoHistorico> findByCodigo(String codigo);

    Optional<CotacaoHistorico> findByCodigoAndData(String codigo, LocalDate data);

    List<CotacaoHistorico> findByData(LocalDate data);

    @Query("SELECT c FROM CotacaoHistorico c ORDER BY ABS(DATEDIFF(DAY, c.data, :data)) ASC")
    List<CotacaoHistorico> findMaisProximaPorData(@Param("data") LocalDate data);

}
