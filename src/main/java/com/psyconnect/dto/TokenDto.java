package com.psyconnect.dto;

public record TokenDto(
		String accessToken,
		String usuario,
		String email,
		String role
		) {

}
