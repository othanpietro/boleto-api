package com.br.boletoapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BoletoException extends BaseException {

    private final String message;
    public BoletoException(String message) {
        super(HttpStatus.BAD_REQUEST);
        this.message = message;
    }
}
