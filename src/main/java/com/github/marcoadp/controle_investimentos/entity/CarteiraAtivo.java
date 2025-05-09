package com.github.marcoadp.controle_investimentos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CarteiraAtivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carteira_ativo_id")
    private Long id;

    @Column(name = "carteira_id")
    private Long carteiraId;

    @Column
    private String codigo;
}
