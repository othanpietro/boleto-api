package com.br.boletoapi.service.impl;

import com.br.boletoapi.model.BoletoDTO;
import com.br.boletoapi.model.PagamentoDTO;
import com.br.boletoapi.model.entidades.Associado;
import com.br.boletoapi.model.entidades.Boleto;
import com.br.boletoapi.repositorios.AssociadoRepository;
import com.br.boletoapi.repositorios.BoletoRepository;
import com.br.boletoapi.service.Impl.BoletoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoletoServiceImplTest {

    private BoletoServiceImpl target;
    private final BoletoRepository boletoRepository = mock(BoletoRepository.class);
    private final AssociadoRepository associadoRepository = mock(AssociadoRepository.class);

    @BeforeEach
    public void setup(){
        target = new BoletoServiceImpl(boletoRepository,associadoRepository);
    }

    @Test
    public void emiteBoletoTest(){
        when(associadoRepository.findByUuid(any())).thenReturn(createAssociado());
        var output = target.emiteBoleto(createBoletoDTO());
        assertNotNull(output);
        assertEquals("123e4567-e89b-12d3-a456-123e4567", output.getUuidAssociado());
        assertEquals(BigDecimal.valueOf(250.25), output.getValor());
        assertEquals(LocalDate.now().plusDays(1), output.getVencimento());
    }

    @Test
    public void pagaBoletoTest(){
        var boleto = createBoletoDTO();
        boleto.setUuid("123e4567-e89b-12d3-a456-123e4567");
        when(boletoRepository.findByUuid(any())).thenReturn(createBoleto());
        var output = target.pagaBoleto(boleto);
        assertNotNull(output);
        assertEquals("123e4567-e89b-12d3-a456-123e4567", output.getUuidAssociado());
        assertEquals(BigDecimal.valueOf(250.25), output.getValor());
        assertEquals(LocalDate.now().plusDays(1), output.getVencimento());


    }
    @Test
    public void pagaBoletoPorAssociadoTest(){
        var associado = createAssociado();
        var boleto = createBoleto();
        boleto.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        when(associadoRepository.findByDocumento(any())).thenReturn(associado);
        when(boletoRepository.findByIndentificador(any())).thenReturn(boleto);
        var output = target.pagaBoletoPorAssociado(createPagamento());
        assertNotNull(output);
        assertEquals("123e4567-e89b-12d3-a456-0000123e4567", output.getUuidAssociado());
        assertEquals(LocalDate.now().plusDays(1), output.getVencimento());
        assertEquals(BigDecimal.valueOf(250.25), output.getValor());
        assertEquals("123e4567-e89b-12d3-a456-0000123e4567", output.getUuid());

    }

    @Test
    public void buscaBoletosPorUuidTest(){
        var associado = createAssociado();
        var boleto = createBoleto();
        boleto.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        when(associadoRepository.findByUuid(any())).thenReturn(associado);
        when(boletoRepository.findByAssociado(any())).thenReturn(List.of(boleto));
        var output = target.buscaBoletosPorUuid("123e4567-e89b-12d3-a456-123e4567");
        assertNotNull(output);

    }

    public BoletoDTO createBoletoDTO(){
        return BoletoDTO
                .builder()
                .uuidAssociado("123e4567-e89b-12d3-a456-123e4567")
                .valor(BigDecimal.valueOf(250.25))
                .vencimento(LocalDate.now().plusDays(1))
                .build();
    }

    public PagamentoDTO createPagamento(){
        return PagamentoDTO
                .builder()
                .documentoAssociado("58799307006")
                .identificador(555555555L)
                .valor(BigDecimal.valueOf(250.25))
                .build();
    }

    private Boleto createBoleto(){
        return Boleto.builder()
                .valor(BigDecimal.valueOf(250.25))
                .documentoPagador("58799307006")
                .indentificador(555555555)
                .situacao("EM_ABERTO")
                .nomePagador("TESTE")
                .nomeFantasiaPagador("TESTE")
                .vencimento(LocalDate.now().plusDays(1))
                .associado(createAssociado())
                .build();
    }
    private Associado createAssociado(){
        return Associado
                .builder()
                .documento("58799307006")
                .tipoPessoa("PF")
                .nome("TESTE")
                .build();
    }

}
