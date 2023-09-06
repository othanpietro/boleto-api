package com.br.boletoapi.repositorios;



import com.br.boletoapi.model.entidades.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, String> {
}
