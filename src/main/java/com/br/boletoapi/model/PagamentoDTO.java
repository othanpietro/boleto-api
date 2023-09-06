package com.br.boletoapi.model;

import lombok.*;

import java.math.BigDecimal;
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoDTO {

    private String documentoAssociado;
    private Long identificador;
    private BigDecimal valor;
}
