package com.psyconnect.services;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.model.Usuario;
import com.psyconnect.repositories.UsuarioRepository;
import com.psyconnect.utils.SenhaUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	public List<UsuarioDto> findAll() {
		return repository.findAll()
				.stream()
				.map(UsuarioDto::new)
				.toList();
	}
	
	public UsuarioDto buscarUsuarioPorEmail(String email) {
		return new UsuarioDto(repository.findByEmail(email));
	}
	
	public void cadastrarUsuario(UsuarioDto usuarioDto) {
		var usuarioEntity = new Usuario(usuarioDto);
		usuarioEntity.setDataInclusao(LocalDateTime.now());
		usuarioEntity.setSenha(SenhaUtils.gerarSenhaAleatoria());
		
		
		repository.save(new Usuario(usuarioDto));
	}
	
	@Transactional(rollbackOn = Exception.class)
	public UsuarioDto atualizarUsuario(UsuarioDto usuarioDto) {
		var entity = repository.findById(usuarioDto.id()).orElseThrow(() -> new ResolutionException(""));
		
		entity.setNome(usuarioDto.nome());
		entity.setEmail(usuarioDto.email());
		entity.setCpf(usuarioDto.cpf());
		entity.setDataNascimento(usuarioDto.dataNascimento());
		entity.setTelefone(usuarioDto.telefone());
		
		return usuarioDto;
		
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void deletarUsuario(Integer id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResolutionException(""));
		entity.setDataFinalizacao(LocalDateTime.now());
	}
}
