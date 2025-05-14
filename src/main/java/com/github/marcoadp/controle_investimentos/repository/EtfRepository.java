package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Etf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtfRepository extends JpaRepository<Etf, Long> {

    Optional<Etf> findFirstByCodigo(String codigo);

    List<Etf> findByTipo(String etf);

}
