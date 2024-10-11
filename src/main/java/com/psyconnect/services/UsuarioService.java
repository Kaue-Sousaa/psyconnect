package com.psyconnect.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.psyconnect.dto.PrimeiroAcessoDto;
import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.enums.UsuarioRoleEn;
import com.psyconnect.exceptions.ResourceNotFoundException;
import com.psyconnect.model.Usuario;
import com.psyconnect.repositories.UsuarioRepository;
import com.psyconnect.strategy.primeiroAcessoStrategy.PrimeiroAcessoStrategy;
import com.psyconnect.strategy.usuarioStrategy.UsuarioStrategy;
import com.psyconnect.utils.SenhaUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	private final List<PrimeiroAcessoStrategy> priAcessoStrategies; 
	private final List<UsuarioStrategy> usuarioStrategy;
	
	private final EmailService emailService;
	
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
		usuarioStrategy.forEach(campo -> campo.validarCampos(usuarioDto));
		
		var usuarioEntity = new Usuario(usuarioDto);
		var senhaGerada = SenhaUtils.gerarSenhaAleatoria();
		usuarioEntity.setDataInclusao(LocalDateTime.now());
		usuarioEntity.setSenha(passwordEncoder(senhaGerada));
		if(usuarioDto.isProfessor()) {
			usuarioEntity.setRole(UsuarioRoleEn.PROFESSOR);
		}
		
		repository.save(usuarioEntity);
		emailService.enviarEmailTexto(usuarioDto.email(), "Bem-vindo(a) ao Sistema PsyConnect - Detalhes da sua Conta", 
					emailConteudo(usuarioEntity.getNome(), usuarioEntity.getEmail(), senhaGerada));
	}
	
	@Transactional(rollbackOn = Exception.class)
	public UsuarioDto atualizarUsuario(UsuarioDto usuarioDto) {
		var entity = repository.findById(usuarioDto.id()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		
		entity.setNome(usuarioDto.nome());
		entity.setEmail(usuarioDto.email());
		entity.setCpf(usuarioDto.cpf());
		entity.setDataNascimento(usuarioDto.dataNascimento());
		entity.setTelefone(usuarioDto.telefone());
		
		return usuarioDto;
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void deletarUsuario(Integer id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		entity.setDataFinalizacao(LocalDateTime.now());
	}
	
	@Transactional(rollbackOn = Exception.class)
	public String primeiroAcesso(String email, PrimeiroAcessoDto primeiroAcesso) {
		priAcessoStrategies.forEach(acesso -> acesso.validarCampos(primeiroAcesso));
		var user = repository.findByEmail(email);
		if(user == null) {
			throw new ResourceNotFoundException("Email não cadastrado");
		}
		user.setPrimeiroAcesso(LocalDateTime.now());
		user.setSenha(passwordEncoder(primeiroAcesso.novaSenha()));
		return "Senha atualizada";
	}
	
	private String passwordEncoder(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	
	private String emailConteudo(String nome, String email, String senha) {
		return "<html>" +
			    "<body>" +
			    "<h1>Cadastro realizado com sucesso!</h1>" +
			    "<p>Olá, " + nome + ",</p>" +
			    "<p>Seu cadastro no sistema foi concluído. Seguem abaixo seus dados de acesso:</p>" +
			    "<p><b>Usuário:</b> " + email + "</p>" +
			    "<p><b>Senha:</b> " + senha + "</p>" +
			    "<p>Por favor, faça login no sistema e altere sua senha o quanto antes.</p>" +
			    "<br>" +
			    "<p>Atenciosamente,</p>" +
			    "<p>Equipe PsyConnect</p>" +
			    "</body>" +
			    "</html>";
	}
}
