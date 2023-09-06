package com.br.boletoapi.exceptions.handler;


import com.br.boletoapi.exceptions.BoletoException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {


    @ExceptionHandler(BoletoException.class)
    public ResponseEntity<ErrorMessage> catchHttpMessageNotReadableException(BoletoException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

}
