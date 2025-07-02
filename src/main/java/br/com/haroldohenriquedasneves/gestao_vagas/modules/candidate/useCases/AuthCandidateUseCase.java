package br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateDTO) throws AuthenticationException {

        // candidateRepository está procurando um candidato pelo username no banco de
        // dados.
        // Se não encontrar, lança uma exceção UsernameNotFoundException com a mensagem
        // "Username/password incorrect."
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect.");
                });

        // passwordMatches está verificando se a senha fornecida pelo candidato
        // corresponde à senha armazenada no banco de dados.
        var passwordMatches = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            // Se a senha não corresponder, lança uma exceção AuthenticationException com a
            // mensagem "Username/password incorrect."
            throw new AuthenticationException("Username/password incorrect.");
        }

        var algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withIssuer("empresaHaroldoHenrique")
                // withClaim adiciona uma reivindicação personalizada ao token JWT.
                // Neste caso, estamos adicionando uma reivindicação chamada "roles" com o
                // valor de uma lista contendo a string "ROLE_CANDIDATE".
                //a role vai aparecer no token JWT
                .withClaim("roles", Arrays.asList("ROLE_CANDIDATE"))
                .withExpiresAt(expiresIn)
                .withSubject(candidate.getId().toString())
                .sign(algorithm);


        // AuthCandidateResponseDTO é uma classe que representa a resposta de autenticação
        // do candidato.
        // Ela é construída usando o padrão Builder do Lombok, que permite criar objetos
        // de forma mais legível e concisa.
        var AuthCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                //toEpochMilli() converte o Instant para milissegundos desde a época (1 de janeiro de 1970).
                // Isso é útil para definir o tempo de expiração do token em milissegundos
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return AuthCandidateResponse;

    }
}
