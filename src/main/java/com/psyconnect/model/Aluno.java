package com.psyconnect.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
	
	private Integer id;
	
	private String nome;
	
	private LocalDate dataNascimento;
	
	private String condição;
	
	private Usuario responsavel;
}
