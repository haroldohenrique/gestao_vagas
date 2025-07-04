package br.com.haroldohenriquedasneves.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    /**
     * Configuração de segurança para desabilitar o CSRF (Cross-Site Request
     * Forgery).
     * 
     * O CSRF é uma proteção contra ataques que tentam executar ações não
     * autorizadas
     * em nome do usuário autenticado. Desabilitar o CSRF pode ser necessário em
     * APIs
     * RESTful, mas deve ser feito com cautela.
     *
     * @param http a configuração de segurança HTTP
     * @return a cadeia de filtros de segurança configurada
     * @throws Exception se ocorrer um erro ao configurar a segurança.
     * 
     */

    // @Bean sobrescreve o método original do SpringBoot, no caso abaixo do
    // SecurityFilterChain.

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll()
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/company/auth").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()
                            .anyRequest().authenticated();
                    // Permite acesso sem autenticação a URLs específicas, como "/candidate/" e
                    // "/company/".
                    // Qualquer outra requisição requer autenticação.
                })
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);

        return http.build();

    }

    /**
     * Configuração do PasswordEncoder para codificar senhas usando o algoritmo
     * BCrypt.
     * 
     * O BCrypt é um algoritmo de hash seguro e amplamente utilizado para armazenar
     * senhas.
     * 
     * @return uma instância de PasswordEncoder configurada com
     *         BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
