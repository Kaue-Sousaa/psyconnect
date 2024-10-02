package com.psyconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psyconnect.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Integer>{

	Usuario findByEmail(String email);

}
