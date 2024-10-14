package com.psyconnect.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.psyconnect.dto.AlunoDto;
import com.psyconnect.services.AlunoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("v1/aluno")
@AllArgsConstructor
public class AlunoController {
	
	private final AlunoService alunoService;
	
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AlunoDto>> findAll(){
		return ResponseEntity.ok(alunoService.buscarTodosAlunos());
	} 
	
	@PostMapping(value = "cadastro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> criarCadastroAluno(@RequestBody AlunoDto alunoDto){
		alunoService.salvarCadastro(alunoDto);
		
	    return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alunoDto.id()) 
                .toUri())
	    		.body("Aluno cadastrado com sucesso");
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>  atualizarCadastro(@RequestBody AlunoDto alunoDto) {
		alunoService.atualizarCadastro(alunoDto);
		return ResponseEntity.ok("Cadastro atualizado");
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> deletarCadastro(@PathVariable Integer id) {
		alunoService.deletarCadastroAluno(id);
		return ResponseEntity.noContent().build();
	}
}
