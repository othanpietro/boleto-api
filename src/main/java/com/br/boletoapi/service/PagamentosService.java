package com.br.boletoapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface PagamentosService {

    public String arquivoPagamentos(MultipartFile file);
}
