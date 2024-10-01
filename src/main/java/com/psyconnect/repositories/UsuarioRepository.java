package com.psyconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psyconnect.dto.UsuarioDto;
import com.psyconnect.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

	UsuarioDto findByEmail(String email);

}
