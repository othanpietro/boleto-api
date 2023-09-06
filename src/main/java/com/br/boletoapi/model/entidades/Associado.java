package com.br.boletoapi.model.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.br.boletoapi.AppConstants.ENTITY_SCHEMA;


@Entity
@Table(
        name = "associado",
        schema = ENTITY_SCHEMA)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Associado extends BaseEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo_pessoa", nullable = false)
    private String tipoPessoa;

    @Column(name = "documento", nullable = false)
    private String documento;


}
