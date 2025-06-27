package br.com.haroldohenriquedasneves.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
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

    @PostMapping("/company")
    public String create(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        // Aqui, você deve chamar o caso de uso de autenticação de empresa
        // e retornar a resposta adequada.
        // Por exemplo, você pode retornar um token JWT ou uma mensagem de sucesso.        
        return this.authCompanyUseCase.execute(authCompanyDTO);

    }
}

