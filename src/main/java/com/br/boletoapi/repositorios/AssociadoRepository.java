package com.br.boletoapi.repositorios;


import com.br.boletoapi.model.entidades.Associado;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface AssociadoRepository extends BaseRepository<Associado>{

    Associado findByUuid(UUID uuid);

    Associado findByDocumento(String documento);

    List<Associado> findAll();
}
