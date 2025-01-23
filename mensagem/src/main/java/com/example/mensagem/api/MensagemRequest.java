package com.example.mensagem.api;

import com.example.mensagem.domain.Mensagem;
import com.example.mensagem.domain.Usuario;

import javax.validation.constraints.NotBlank;

public class MensagemRequest {

    @NotBlank
    private String conteudo;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    public Mensagem converterParaUsuario(Usuario autor){
        return new Mensagem(autor, conteudo);
    }
}
