package br.com.haroldohenriquedasneves.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException(){
        super("Usuário já existe");
        //super aqui vai invocar o construtor da classe pai(ou seja, construtor default da RuntimeException)
    }
}
