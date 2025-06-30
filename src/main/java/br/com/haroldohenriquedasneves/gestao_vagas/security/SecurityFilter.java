package br.com.haroldohenriquedasneves.gestao_vagas.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    // ctrl + . para implementar o método doFilterInternal

    //esse filtro é responsável por interceptar as requisições HTTP e aplicar a lógica de autenticação e autorização.
    // Ele é executado uma vez por requisição, garantindo que a lógica de segurança seja aplicada de forma consistente em todas as requisições.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
                String header = request.getHeader("Authorization");
                System.out.println(header);

        //filterChain está fazendo a requisição passar pelo filtro, ou seja, ele chama o próximo filtro na cadeia de filtros.
        // Isso é importante para garantir que a requisição continue seu fluxo normal após passar pelo filtro
        filterChain.doFilter(request, response);
    }

}
