package com.br.boletoapi.service;


import com.br.boletoapi.model.BoletoDTO;
import com.br.boletoapi.model.PagamentoDTO;
import com.br.boletoapi.model.entidades.Boleto;

import java.util.List;

public interface BoletoService {
    public BoletoDTO emiteBoleto(BoletoDTO boletoDTO);

    public BoletoDTO pagaBoleto(BoletoDTO boleto);

    public BoletoDTO pagaBoletoPorAssociado(PagamentoDTO pagamentoDTO);

    public List<Boleto> buscaBoletosPorUuid(String uuid);
}
