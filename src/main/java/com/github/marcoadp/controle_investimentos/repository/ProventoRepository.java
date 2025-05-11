package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Provento;
import com.github.marcoadp.controle_investimentos.enums.TipoProventoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProventoRepository extends JpaRepository<Provento, Long> {

    List<Provento> findByCodigo(String codigo);

    List<Provento> findByTipoProvento(TipoProventoEnum tipoProvento);

}
