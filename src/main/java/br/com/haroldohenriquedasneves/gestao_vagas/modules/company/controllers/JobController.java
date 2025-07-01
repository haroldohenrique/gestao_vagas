package br.com.haroldohenriquedasneves.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.entities.JobEntity;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        //builder esta sendo usado para criar o objeto JobEntity
        //isso é uma boa prática, pois permite criar objetos de forma mais legível e organizada
        //uuid.fromString é usado para converter uma string em um UUID
        //a classe JobEntity é uma entidade que representa um trabalho na aplicação
        var jobEntity = JobEntity.builder()
                .benefits(createJobDTO.getBenefits())
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobDTO.getDescription())
                .level(createJobDTO.getLevel())
                .build();
        return this.createJobUseCase.execute(jobEntity);
    }
}
