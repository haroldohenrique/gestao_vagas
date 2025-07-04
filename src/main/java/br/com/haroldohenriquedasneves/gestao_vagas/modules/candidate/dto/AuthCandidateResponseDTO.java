package br.com.haroldohenriquedasneves.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@builder faz com que o lombok gere um construtor com todos os parâmetros
//@Data gera os métodos getters e setters, toString, equals e hashCode
//@noArgsConstructor gera um construtor sem parâmetros
//@AllArgsConstructor gera um construtor com todos os parâmetros
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateResponseDTO {
    private String access_token;
    private Long expires_in;

}
