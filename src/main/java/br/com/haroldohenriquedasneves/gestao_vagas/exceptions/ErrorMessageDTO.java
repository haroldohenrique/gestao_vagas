package br.com.haroldohenriquedasneves.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

// data transfer to object
@Data
@AllArgsConstructor //faz um construtor com todos os argumentos da classe
public class ErrorMessageDTO {
    private String message;
    private String field;

}
