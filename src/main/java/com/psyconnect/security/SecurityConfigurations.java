package com.psyconnect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.psyconnect.enums.UsuarioRoleEn;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurations {
	
	private static final String ADMIN = UsuarioRoleEn.ADMIN.getDescricao();
	private static final String PROFESSOR = UsuarioRoleEn.PROFESSOR.getDescricao();
	
	private final SecurityFilter securityFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login", "v1/usuario/cadastro", "v1/usuario/primeiro-acesso/**").permitAll()
						.requestMatchers(HttpMethod.GET, "v1/usuario/**", "v1/aluno/**").hasAnyRole(ADMIN, PROFESSOR)
						.requestMatchers(HttpMethod.POST, "v1/aluno/cadastro").hasAnyRole(ADMIN, PROFESSOR)
						.requestMatchers(HttpMethod.PUT, "v1/usuario", "v1/aluno").hasAnyRole(ADMIN, PROFESSOR)
						.requestMatchers(HttpMethod.DELETE, "v1/usuario/**", "v1/aluno/**").hasRole(ADMIN)
						.anyRequest().authenticated()
				)
				.exceptionHandling(this::configureExceptionHandling)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exception) {
	    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getWriter().write("Acesso Negado!");
	    });
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
