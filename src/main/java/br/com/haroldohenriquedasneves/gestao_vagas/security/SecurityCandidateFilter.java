package br.com.haroldohenriquedasneves.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.haroldohenriquedasneves.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//OncePerRequestFilter é uma classe base para filtros que garantem que o filtro seja executado apenas uma vez por solicitação.
//Ela é útil para implementar filtros que precisam ser aplicados a cada solicitação HTTP, como autenticação, autorização ou logging.
@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCandidateProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // SecurityContextHolder faz parte do Spring Security e é usado para armazenar o
        // contexto de segurança atual, que inclui informações sobre o usuário
        // autenticado e suas permissões.
        // Ele é usado para acessar informações de autenticação e autorização em
        // qualquer parte da aplicação Spring Security.
        // SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {
            if (header != null) {
                var token = this.jwtProvider.validateToken(header);
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                request.setAttribute("candidate_id", token.getSubject());
                var roles = token.getClaim("roles").asList(Object.class);

                var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                        null,
                        grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }

        filterChain.doFilter(request, response);
    }

}
