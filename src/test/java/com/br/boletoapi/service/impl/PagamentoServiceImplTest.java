package com.br.boletoapi.service.impl;

import com.br.boletoapi.kafka.TopicProducer;
import com.br.boletoapi.service.Impl.PagamentosServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class PagamentoServiceImplTest {

    private PagamentosServiceImpl target;
    private final TopicProducer topicProducer = mock(TopicProducer.class);

    @BeforeEach
    public void setup(){
        target = new PagamentosServiceImpl(topicProducer);
    }

    @Test
    public void arquivoPagamentos(){
        var output = target.arquivoPagamentos(createMultipartFile());
        assertNotNull(output);
        assertEquals("Mensagem publicada com sucesso.", output);
    }

    private MultipartFile createMultipartFile(){
        byte[] array = Base64.getDecoder().decode("MDAwNTg3OTkzMDcwMDY1NTU1NTU1NTU1NTUgIDAwMDAwMDAwMDAwMDAwMDI1MDI1");

        return new MockMultipartFile("file",array);
    }

}
