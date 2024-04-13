package com.padroes.java.spring.dto;

import com.padroes.java.spring.model.Endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDto {
	
	private Long id;
	private String nome;
	private Endereco endereco;

}
