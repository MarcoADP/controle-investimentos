package com.github.marcoadp.controle_investimentos.repository;

import com.github.marcoadp.controle_investimentos.entity.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {

    Optional<Acao> findFirstByCodigo(String codigo);

    @Query("FROM acao_br a where a.setor.id = :setorId")
    List<Acao> findBySetor(Long setorId);

}
