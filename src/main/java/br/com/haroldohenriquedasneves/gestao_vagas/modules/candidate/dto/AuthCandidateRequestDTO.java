package br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.dto;

//record é uma classe que é usada para armazenar dados imutáveis
// É uma maneira concisa de definir uma classe que contém apenas campos e não tem lógica adicional
public record AuthCandidateRequestDTO(String username, String password) {
    
}
