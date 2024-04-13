package com.padroes.java.spring.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.padroes.java.spring.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

	Optional<Endereco> findByCep(String cep);
}
