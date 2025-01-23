package com.example.mensagem.api;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mensagem.domain.Mensagem;
import com.example.mensagem.domain.MensagemRepository;
import com.example.mensagem.domain.Usuario;
import com.example.mensagem.security.SegurancaService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {
	
	private final Logger logger = LoggerFactory.getLogger(MensagemController.class);
	private final MensagemRepository mensagemRepository;
	private final SegurancaService segurancaService;

	public MensagemController(MensagemRepository mensagemRepository,
							  SegurancaService segurancaService) {
		this.mensagemRepository = mensagemRepository;
		this.segurancaService = segurancaService;
	}

	@GetMapping
	@Counted(value = "mensagem.count.buscarPaginado")
	@Timed(value = "mensagem.timed.buscarPaginado", longTask = true)
	public Page<MensagemResponse> buscarPaginado(Pageable pageable) {
		return mensagemRepository.findAll(pageable)
				.map(MensagemResponse::daMensagem);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemResponse criarNovo(@RequestBody @Valid MensagemRequest mensagemRequest) {
		final Usuario autor = segurancaService.getUsuarioOuFalhe();
		final Mensagem mensagem = mensagemRequest.converterParaUsuario(autor);
		
		logger.debug("Nova Mensagem criada");
		
		return MensagemResponse.daMensagem(mensagemRepository.save(mensagem));
	}

	@GetMapping("/{id}")
	public MensagemResponse buscarPorId(@PathVariable Long id) {
		return MensagemResponse.daMensagem(mensagemRepository.findById(id)
				.orElseThrow(RecursoNaoEncontradoException::new));
	}
}
