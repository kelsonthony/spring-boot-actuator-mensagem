package com.example.mensagem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ActuatorUserDetailsServiceConfig {

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
		final var manager = new InMemoryUserDetailsManager();
		final var usuario = User.withUsername("actuator")
				.password(passwordEncoder.encode("123"))
				.roles("ACTUATOR")
				.build();
		manager.createUser(usuario);
		
		return manager;
	}
}
