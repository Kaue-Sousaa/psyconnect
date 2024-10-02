package com.psyconnect.utils;

import java.security.SecureRandom;

public class SenhaUtils {
	
	private static final int TAMANHO_SENHA = 8;
	private static final String CARACTERES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+-=[]|,./?><";

	public static String gerarSenhaAleatoria() {
		SecureRandom random = new SecureRandom();
		StringBuilder senha = new StringBuilder(TAMANHO_SENHA);

		for (int i = 0; i < TAMANHO_SENHA; i++) {
			int index = random.nextInt(CARACTERES.length());
			senha.append(CARACTERES.charAt(index));
		}

		return senha.toString();
	}
}
