package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.security.ServicoDeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private ServicoDeToken servicoDeToken;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody DadosLogin dados) {
        var tokenDeAutenticacao = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        
        // O Spring verifica no banco se a senha (em hash) bate com a digitada
        var autenticacao = manager.authenticate(tokenDeAutenticacao);
        
        // Se passar, gera o token
        var tokenJWT = servicoDeToken.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}

// Records para não poluir o projeto com arquivos DTO minúsculos
record DadosLogin(String email, String senha) {}
record DadosTokenJWT(String token) {}