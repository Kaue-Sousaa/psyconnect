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
	    validarCampos(usuarioDto);
	    
	    var senhaGerada = SenhaUtils.gerarSenhaAleatoria();
	    var usuario = criarUsuario(usuarioDto, senhaGerada);
	    atribuirRoleSeProfessor(usuarioDto, usuario);
	    
	    salvarUsuario(usuario);
	    
	    enviarEmailBoasVindas(usuario, senhaGerada);
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
	public String primeiroAcesso(PrimeiroAcessoDto primeiroAcessoDto) {
		priAcessoStrategies.forEach(acesso -> acesso.validarCampos(primeiroAcessoDto));
		var user = repository.findByEmail(primeiroAcessoDto.email());
		if(user == null) {
			throw new ResourceNotFoundException("Email não cadastrado");
		}
		user.setPrimeiroAcesso(LocalDateTime.now());
		user.setSenha(passwordEncoder(primeiroAcessoDto.novaSenha()));
		return "Senha atualizada";
	}
	
	private void validarCampos(UsuarioDto usuarioDto) {
	    usuarioStrategy.forEach(campo -> campo.validarCampos(usuarioDto));
	}

	private Usuario criarUsuario(UsuarioDto usuarioDto, String senhaGerada) {
	    var usuario = new Usuario(usuarioDto);
	    usuario.setDataInclusao(LocalDateTime.now());
	    usuario.setSenha(passwordEncoder(senhaGerada));
	    
	    return usuario;
	}

	private void atribuirRoleSeProfessor(UsuarioDto usuarioDto, Usuario usuario) {
	    if (usuarioDto.isProfessor()) {
	        usuario.setRole(UsuarioRoleEn.PROFESSOR.getDescricao());
	    }
	}

	private void salvarUsuario(Usuario usuario) {
	    repository.save(usuario);
	}

	private void enviarEmailBoasVindas(Usuario usuario, String senhaGerada) {
	    emailService.enviarEmailTexto(
	    		usuario.getEmail(), 
	        "Bem-vindo(a) ao Sistema PsyConnect - Detalhes da sua Conta", 
	        emailConteudo(usuario.getNome(), usuario.getEmail(), senhaGerada)
	    );
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
	
	private String passwordEncoder(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
}
