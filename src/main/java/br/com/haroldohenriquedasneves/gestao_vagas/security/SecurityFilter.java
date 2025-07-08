package br.com.haroldohenriquedasneves.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.haroldohenriquedasneves.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    // ctrl + . para implementar o método doFilterInternal

    // esse filtro é responsável por interceptar as requisições HTTP e aplicar a
    // lógica de autenticação e autorização.
    // Ele é executado uma vez por requisição, garantindo que a lógica de segurança
    // seja aplicada de forma consistente em todas as requisições.

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");

        // header é onde tá o bearer token, que é o token de autenticação que o usuário
        // envia na requisição.

        if (request.getRequestURI().startsWith("/company")) {

            if (header != null) {
                var subjectToken = this.jwtProvider.validateToken(header);
                if (subjectToken.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                // Se o token for válido, o subjectToken conterá o ID da empresa associada ao
                // token.
                request.setAttribute("company_id", subjectToken);

                // Aqui, estamos criando um objeto de autenticação com o ID da empresa
                // extraído do token.
                // O UsernamePasswordAuthenticationToken é uma implementação de
                // Authentication que representa um usuário autenticado com nome de usuário e
                // senha.
                // No caso, estamos usando o ID da empresa como nome de usuário e não estamos
                // usando senha, então passamos null para o segundo parâmetro.
                // O terceiro parâmetro é uma coleção de autoridades (roles) do usuário, que
                // estamos deixando vazia (Collections.emptyList()) porque não estamos
                // lidando com roles neste exemplo.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null,
                        Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }

        // filterChain está fazendo a requisição passar pelo filtro, ou seja, ele chama
        // o próximo filtro na cadeia de filtros.
        // Isso é importante para garantir que a requisição continue seu fluxo normal
        // após passar pelo filtro
        filterChain.doFilter(request, response);
    }

}
