package com.br.boletoapi.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoletoDTO {

    private String uuid;
    private BigDecimal valor;
    private String uuidAssociado;
    private LocalDate vencimento;
}
