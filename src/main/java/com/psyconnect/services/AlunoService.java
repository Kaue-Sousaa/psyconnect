package com.psyconnect.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.psyconnect.dto.AlunoDto;
import com.psyconnect.exceptions.ExistingObjectException;
import com.psyconnect.exceptions.ResourceNotFoundException;
import com.psyconnect.model.Aluno;
import com.psyconnect.repositories.AlunoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlunoService {
	
	private static final String ALUNO_NAO_ENCONTRADO = "Aluno não encontrado";
	private final AlunoRepository alunoRepository;
	
	public List<AlunoDto> buscarTodosAlunos(){
		return alunoRepository.findAll()
				.stream()
				.map(AlunoDto::new).toList();
	}
	
	public void salvarCadastro(AlunoDto alunoDto) {
		var entity = alunoRepository.findByNomeIgnoreCase(alunoDto.nome());
		if(entity.isPresent()) {
			throw new ExistingObjectException("Aluno já cadastrado");
		}
		var aluno = new Aluno(alunoDto);
		aluno.setDataInclusao(LocalDateTime.now());
		
		alunoRepository.save(aluno);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void atualizarCadastro(AlunoDto alunoDto) {
		var entity = alunoRepository.findById(alunoDto.id()).
				orElseThrow(() -> new ResourceNotFoundException(ALUNO_NAO_ENCONTRADO));
		
		entity.setNome(alunoDto.nome());
		entity.setDataNascimento(alunoDto.dataNascimento());
		entity.setHabilidade(alunoDto.habilidade());
		entity.setDificuldade(alunoDto.dificuldade());
		entity.setResponsavel(alunoDto.responsavel());
	}
	
	public void deletarCadastroAluno(Integer id) {
		var aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ALUNO_NAO_ENCONTRADO));
		alunoRepository.delete(aluno);
	}
}
