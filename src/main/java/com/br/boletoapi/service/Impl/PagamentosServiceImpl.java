package com.br.boletoapi.service.Impl;


import com.br.boletoapi.exceptions.BoletoException;
import com.br.boletoapi.kafka.TopicProducer;
import com.br.boletoapi.model.PagamentoFileDTO;
import com.br.boletoapi.service.PagamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PagamentosServiceImpl implements PagamentosService {

    private final TopicProducer topicProducer;

    @Override
    public String arquivoPagamentos(MultipartFile file) {

        try {
            var pagamentos = Base64.getEncoder().encodeToString(file.getBytes());
            topicProducer.send(pagamentos);
            return "Mensagem publicada com sucesso.";

        }catch (Exception ex){
            throw new BoletoException("Erro produzir carga no kafka. " + ex.getMessage());
        }
    }

}
