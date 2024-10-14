package com.psyconnect.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.enums.UsuarioRoleEn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuario_usu", schema = "sistema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_usuario_usu")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_id_usuario_usu")
	@SequenceGenerator(name = "seq_id_usuario_usu", sequenceName = "sistema.seq_id_usuario_usu", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@Column(name = "data_inclusao_usu")
	private LocalDateTime dataInclusao;
	
	@Column(name = "data_finalizacao_usu")
	private LocalDateTime dataFinalizacao;
	
	@Column(name = "nome_usu", nullable = false, length = 255)
	private String nome;
	
	@Column(name = "email_usu", nullable = false)
	private String email;
	
	@Column(name = "cpf_usu", nullable = false, length = 11)
	private String cpf;
	
	@Column(name = "data_nascimento_usu", nullable = false)
	private LocalDate dataNascimento;
	
	@Column(name = "telefone_usu")
	private String telefone;
	
	@Column(name = "senha_usu", nullable = false)
	private String senha;
	
	@Column(name = "role_usu", nullable = false)
	private String role;
	
	@Column(name = "flag_professor_usu", nullable = false)
	private boolean isProfessor;
	
	@Column(name = "data_primeiro_acesso_usu")
	private LocalDateTime primeiroAcesso;
	
	@Transient
	private String confirmSenha;
	
	public Usuario(UsuarioDto dto) {
		this.id = dto.id();
		this.dataInclusao = dto.dataInclusao();
		this.dataFinalizacao = dto.dataFinalizacao();
		this.nome = dto.nome();
		this.email = dto.email();
		this.cpf = dto.cpf();
		this.dataNascimento = dto.dataNascimento();
		this.telefone = dto.telefone();
		this.senha = dto.senha();
		this.role = dto.role();
		this.isProfessor = dto.isProfessor();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role.equals(UsuarioRoleEn.ADMIN.name()))
			return List.of(
					new SimpleGrantedAuthority("ROLE_ADMIN"), 
					new SimpleGrantedAuthority("ROLE_USUARIO"), 
					new SimpleGrantedAuthority("ROLE_PROFESSOR"));
		if ((this.role.equals(UsuarioRoleEn.USUARIO.name())))
			return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}
}