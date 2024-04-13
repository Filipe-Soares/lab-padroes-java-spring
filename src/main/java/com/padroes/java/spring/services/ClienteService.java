package com.padroes.java.spring.services;

import com.padroes.java.spring.dto.ClienteDto;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface ClienteService {

	Iterable<ClienteDto> buscarTodos();

	ClienteDto buscarPorId(Long id);

	void inserir(ClienteDto cliente);

	void atualizar(Long id, ClienteDto cliente);

	void deletar(Long id);

}
