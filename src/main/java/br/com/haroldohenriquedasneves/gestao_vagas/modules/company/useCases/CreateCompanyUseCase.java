package br.com.haroldohenriquedasneves.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.haroldohenriquedasneves.gestao_vagas.exceptions.UserFoundException;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
    // Implementação do caso de uso para criar uma empresa
    // Este caso de uso deve conter a lógica para validar os dados da empresa,
    // persistir a empresa no banco de dados e retornar o resultado da operação.

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        // Aqui, estamos usando o PasswordEncoder para codificar a senha antes de
        // salvar a entidade no banco de dados.

        return this.companyRepository.save(companyEntity);
    }

}
