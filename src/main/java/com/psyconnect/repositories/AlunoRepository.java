package com.psyconnect.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psyconnect.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
	
	Optional<Aluno> findByNomeIgnoreCase(String nome);
}
