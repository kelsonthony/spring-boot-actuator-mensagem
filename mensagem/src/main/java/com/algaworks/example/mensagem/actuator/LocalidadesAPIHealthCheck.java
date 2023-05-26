package com.algaworks.example.mensagem.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.algaworks.example.mensagem.integration.LocalidadesClient;

@Component("localidadesHealth")
public class LocalidadesAPIHealthCheck implements HealthIndicator {
	
	@Autowired
	private LocalidadesClient localidadesClient;

	@Override
	public Health health() {

		if (localidadesClient.estaOnline()) {
			return Health.up().withDetail("mensagem", "Comunicação OK.").build();
		}
		
		return Health.down().withDetail("mensagem", "Comunicação Falhou.").build();
	}

}
