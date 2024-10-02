package com.psyconnect.enums;

public enum UsuarioRoleEn {
    
	ADMIN(1, "ADMINISTRADOR"),
    PROFESSOR(1, "PROFESSOR"),
    USUARIO(2, "USUARIO");

    private final int id;
    private final String descricao;

    private UsuarioRoleEn(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static UsuarioRoleEn findById(int id) {
        for (UsuarioRoleEn usuario : values()) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("ID não encontrado: " + id);
    }

    public static UsuarioRoleEn findByDescricao(String descricao) {
        for (UsuarioRoleEn usuario : values()) {
            if (usuario.getDescricao().equalsIgnoreCase(descricao)) {
                return usuario;
            }
        }
        throw new IllegalArgumentException("Descrição não encontrada: " + descricao);
    }
}

