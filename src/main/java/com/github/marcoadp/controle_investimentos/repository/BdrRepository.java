package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Bdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BdrRepository extends JpaRepository<Bdr, Long> {

    Optional<Bdr> findFirstByCodigo(String codigo);

    @Query("FROM bdr where setor = :setor")
    List<Bdr> findBySetor(String setor);

}
