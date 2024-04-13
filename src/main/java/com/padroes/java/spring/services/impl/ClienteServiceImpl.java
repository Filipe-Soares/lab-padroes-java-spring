package com.padroes.java.spring.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.padroes.java.spring.dto.ClienteDto;
import com.padroes.java.spring.model.Cliente;
import com.padroes.java.spring.model.Endereco;
import com.padroes.java.spring.repositories.ClienteRepository;
import com.padroes.java.spring.repositories.EnderecoRepository;
import com.padroes.java.spring.services.ClienteService;
import com.padroes.java.spring.services.ViaCepService;
import com.padroes.java.spring.services.exception.ObjectNotFoundException;
import com.padroes.java.spring.services.exception.SystemPolicyException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr e Filipe Soares
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	private final ClienteRepository clienteRepository;
	private final EnderecoRepository enderecoRepository;
	private final ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	@Transactional
	public Iterable<ClienteDto> buscarTodos() {
		// Buscar todos os Clientes.
		List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
		List<ClienteDto> clientesDto = clientes.stream().map(cli -> ClienteDto.builder().id(cli.getId()).nome(cli.getNome()).endereco(cli.getEndereco()).build()).collect(Collectors.toList());
		return clientesDto;
	}

	@Override
	public ClienteDto buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Cliente cliente = clienteRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado"));
		return ClienteDto.builder().id(cliente.getId()).nome(cliente.getNome()).endereco(cliente.getEndereco()).build();
	}

	@Override
	@Transactional
	public void inserir(ClienteDto clienteDto) {
		Cliente cliente = new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEndereco());
		salvarClienteComCep(cliente);
	}

	@Override
	@Transactional
	public void atualizar(Long id, ClienteDto clienteDto) {
		// Buscar Cliente por ID, caso exista:
		clienteRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Cliente não encontrado"));
		Cliente cliente = new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEndereco());
			salvarClienteComCep(cliente);
		
	}

	@Override
	@Transactional
	public void deletar(Long id) {
		// Deletar Cliente por ID.
		clienteRepository.deleteById(id);
	}

	private void salvarClienteComCep(Cliente cliente) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		if(!cep.matches("\\d{5}-\\d{3}"))
			throw new SystemPolicyException("Formato do cep informado é inválido. Formato aceito Ex: 60000-000");
		Endereco endereco = enderecoRepository.findByCep(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		clienteRepository.save(cliente);
	}

}