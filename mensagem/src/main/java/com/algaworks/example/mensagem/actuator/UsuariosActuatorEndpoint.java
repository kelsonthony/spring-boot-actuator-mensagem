package com.algaworks.example.mensagem.actuator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.algaworks.example.mensagem.domain.UsuarioRepository;

@RestControllerEndpoint(id = "usuarios")
@Component
public class UsuariosActuatorEndpoint {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public List<String> usuarioRaiz() {
		return Arrays.asList("usuarios-contagem");
	}
	
	@GetMapping("/usuarios-contagem")
	public Map<String, Object> usuariosContagem() {
		final HashMap<String, Object> resposta = new HashMap<>();
		
		resposta.put("quantidade", usuarioRepository.count());
		
		return resposta;
	}
	
	
	
}
