package br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.haroldohenriquedasneves.gestao_vagas.exceptions.UserFoundException;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.CandidateRepository;


@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        // Check if the candidate already exists by username or email
        // If it exists, throw an exception
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        // If it does not exist, save the candidate entity
        // and return the saved entity
        return this.candidateRepository.save(candidateEntity);
    };

}