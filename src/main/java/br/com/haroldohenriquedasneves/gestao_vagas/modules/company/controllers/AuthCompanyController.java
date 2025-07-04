package br.com.haroldohenriquedasneves.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {
    // Implementação do controlador para autenticação de empresas
    // Este controlador deve receber as requisições de autenticação, chamar o caso
    // de uso
    // de autenticação e retornar a resposta adequada.
    // Você pode usar anotações como @PostMapping, @RequestBody, etc., para definir
    // os
    // endpoints e os parâmetros esperados.

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        // Aqui, você deve chamar o caso de uso de autenticação de empresa
        // e retornar a resposta adequada.
        // Por exemplo, você pode retornar um token JWT ou uma mensagem de sucesso.
        try {
            var result = authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            //aqui está tratando a exceção de autenticação
            // Você pode personalizar a mensagem de erro conforme necessário.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
