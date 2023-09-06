package com.br.boletoapi.service.Impl;


import com.br.boletoapi.exceptions.BoletoException;
import com.br.boletoapi.model.BoletoDTO;
import com.br.boletoapi.model.PagamentoDTO;
import com.br.boletoapi.model.entidades.Associado;
import com.br.boletoapi.model.entidades.Boleto;
import com.br.boletoapi.repositorios.AssociadoRepository;
import com.br.boletoapi.repositorios.BoletoRepository;
import com.br.boletoapi.service.BoletoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoletoServiceImpl implements BoletoService {

    private final BoletoRepository boletoRepository;
    private final AssociadoRepository associadoRepository;

    @Override
    public BoletoDTO emiteBoleto(BoletoDTO boletoDTO) {
        try{
            if (validaDto(boletoDTO)){
                throw new BoletoException("DTO invalido.");
            }
            Associado associado = associadoRepository.findByUuid(UUID.fromString(boletoDTO.getUuidAssociado()));

            if(ObjectUtils.isEmpty(associado)){
                throw new BoletoException("Associado não cadastrado. ");
            }

            if(boletoDTO.getVencimento().isBefore(LocalDate.now())){
                throw new BoletoException("Data vencimento menor que data atual. ");
            }
            boletoRepository.save(boletoFromBoletoDTO(boletoDTO,associado));

        }catch (Exception ex){
            throw new BoletoException("Erro ao gerar boleto. " + ex.getMessage());
        }

        return boletoDTO;
    }

    @Override
    public BoletoDTO pagaBoleto(BoletoDTO boletoDTO) {
        try{
            if (validaDto(boletoDTO)){
                throw new BoletoException("DTO invalido.");
            }
            var boleto = boletoRepository.findByUuid(UUID.fromString(boletoDTO.getUuid()));
            if(ObjectUtils.isEmpty(boleto)){
                throw new BoletoException("Boleto não cadastrado. ");
            }

            if(boleto.getVencimento().isBefore(LocalDate.now())){
                throw new BoletoException("Data vencimento menor que data atual. ");
            }
            if(!boleto.getValor().equals(boletoDTO.getValor())){
                throw new BoletoException("Valor divergente do atual. ");
            }
            if(boleto.getSituacao().equals("PAGO")){
                throw new BoletoException("Boleto já pago. ");
            }

            boleto.setSituacao("PAGO");

            boletoRepository.save(boleto);

            return boletoDTO;

        }catch (Exception ex){
            throw new BoletoException("Erro ao pagar boleto. " + ex.getMessage());
        }

    }

    @Override
    public BoletoDTO pagaBoletoPorAssociado(PagamentoDTO pagamentoDTO) {
        try {
            if (validaPagamento(pagamentoDTO)){
                throw new BoletoException("DTO invalido.");
            }
            Associado associado = associadoRepository.findByDocumento(pagamentoDTO.getDocumentoAssociado());

            if(ObjectUtils.isEmpty(associado)){
                throw new BoletoException("Associado não cadastrado. ");
            }

            var boleto = boletoRepository.findByIndentificador(pagamentoDTO.getIdentificador());

            if(ObjectUtils.isEmpty(boleto)){
                throw new BoletoException("Boleto não cadastrado. ");
            }

            if(!boleto.getDocumentoPagador().equals(associado.getDocumento())){
                throw new BoletoException("Boleto não pertence ao assocido. ");
            }

            if(boleto.getVencimento().isBefore(LocalDate.now())){
                throw new BoletoException("Data vencimento menor que data atual. ");
            }
            if(!boleto.getValor().equals(pagamentoDTO.getValor())){
                throw new BoletoException("Valor divergente do atual. ");
            }


            if(boleto.getSituacao().equals("PAGO")){
                throw new BoletoException("Boleto já pago. ");
            }


            boleto.setSituacao("PAGO");

            boletoRepository.save(boleto);

            return BoletoDTO.builder()
                    .uuidAssociado(associado.getUuid().toString())
                    .vencimento(boleto.getVencimento())
                    .valor(boleto.getValor())
                    .uuid(boleto.getUuid().toString())
                    .build();

        }catch (Exception ex){
            throw new BoletoException("Erro ao pagar boleto por documento do associado. " + ex.getMessage());
        }
    }

    @Override
    public List<Boleto> buscaBoletosPorUuid(String uuid) {
        try{

            Associado associado = associadoRepository.findByUuid(UUID.fromString(uuid));

            if(ObjectUtils.isEmpty(associado)){
                throw new BoletoException("Associado não cadastrado. ");
            }

            return boletoRepository.findByAssociado(associado);

        }catch (Exception ex){
            throw new BoletoException("Erro ao gerar boleto. " + ex.getMessage());
        }
    }

    protected boolean validaDto(BoletoDTO dto){

        return ObjectUtils.isEmpty(dto.getUuidAssociado()) ||
                ObjectUtils.isEmpty(dto.getValor()) ||
                ObjectUtils.isEmpty(dto.getVencimento());

    }
    protected boolean validaPagamento(PagamentoDTO dto){

        return ObjectUtils.isEmpty(dto.getIdentificador()) ||
                ObjectUtils.isEmpty(dto.getValor()) ||
                ObjectUtils.isEmpty(dto.getDocumentoAssociado());

    }


    protected Boleto boletoFromBoletoDTO(BoletoDTO boletoDTO, Associado associado){
        Random ramdom = new Random();
       return Boleto.builder()
                .associado(associado)
                .valor(boletoDTO.getValor())
                .situacao("EM_ABERTO")
                .documentoPagador(associado.getDocumento())
                .nomePagador(associado.getNome())
                .nomeFantasiaPagador(associado.getNome())
                .vencimento(boletoDTO.getVencimento())
                .indentificador(ramdom.nextInt(2147483647))
                .build();
    }

}
