package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.FundoImobiliario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundoImobiliarioRepository extends JpaRepository<FundoImobiliario, Long>, JpaSpecificationExecutor<FundoImobiliario> {

    Optional<FundoImobiliario> findFirstByCodigo(String codigo);

}
