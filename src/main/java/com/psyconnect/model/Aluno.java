package com.psyconnect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.psyconnect.dto.AlunoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_aluno_alu", schema = "sistema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_id_aluno_alu")
	@SequenceGenerator(name = "seq_id_aluno_alu", sequenceName = "sistema.seq_id_aluno_alu", initialValue = 1, allocationSize = 1)
	@Column(name = "id_alu")
	private Integer id;
	
	@Column(name = "nome_alu", nullable = false)
	private String nome;
	
	@Column(name = "data_inclusao_alu")
	private LocalDateTime dataInclusao;
	
	@Column(name = "data_finalizacao_alu")
	private LocalDateTime dataFinalizacao;
	
	@Column(name = "data_nascimento_alu")
	private LocalDate dataNascimento;
	
	@Column(name = "habilidade_alu")
	private String habilidade;
	
	@Column(name = "dificuldade_alu")
	private String dificuldade;
	
	@Column(name = "responsavel_alu", nullable = false)
	private String responsavel;
	
	public Aluno(AlunoDto dto) {
		this.id = dto.id();
		this.nome = dto.nome();
		this.dataNascimento = dto.dataNascimento();
		this.habilidade = dto.habilidade();
		this.dificuldade = dto.dificuldade();
		this.responsavel = dto.responsavel();
	}
}
