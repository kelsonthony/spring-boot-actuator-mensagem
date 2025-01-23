package com.example.mensagem.actuator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.mensagem.domain.MensagemRepository;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

@Configuration
public class MetricasCustomConfig {

	@Bean
	public MeterBinder quantidadeMensagens(MensagemRepository mensagemRepository) {
		return (meterRegistry) -> {
			Gauge.builder("mensagem.qtd", mensagemRepository::count)
				.register(meterRegistry);
		};
	}
	
	@Bean
	public CountedAspect countedAspect(MeterRegistry meterRegistry) {
		return new CountedAspect(meterRegistry);
	}
	
	@Bean
	public TimedAspect timedAspect(MeterRegistry meterRegistry) {
		return new TimedAspect();
	}
}
