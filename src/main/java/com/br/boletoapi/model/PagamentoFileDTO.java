package com.br.boletoapi.model;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoFileDTO {

    private String file;

    @Override
    public String toString() {
        return "{" +
                "file='" + file + '\'' +
                '}';
    }
}
