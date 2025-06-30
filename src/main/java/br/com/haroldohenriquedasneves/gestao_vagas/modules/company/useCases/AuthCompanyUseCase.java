package br.com.haroldohenriquedasneves.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.haroldohenriquedasneves.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername())
                // orElseThrow ta lançando uma exceção se a empresa não for encontrada.
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect.");
                });
        var passwordMatches = passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se a autenticação for bem-sucedida, você pode retornar a empresa ou algum
        // token de autenticação.
        // Por exemplo, você pode retornar a empresa autenticada ou um token JWT.
        // Aqui em baixo, tem o algorithm com a secret
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        // withIssuer é o nome do emissor do token, que pode ser o nome da sua empresa
        // ou
        // qualquer outro identificador que você desejar.
        // withSubject é o assunto do token, que pode ser o ID da empresa ou qualquer
        // outro identificador exclusivo.
        var token = JWT.create().withIssuer("empresaHaroldoHenrique")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString()).sign(algorithm);
        return token;
        // Retorne o token ou a empresa autenticada conforme necessário.
    }

}
