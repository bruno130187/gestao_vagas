package br.com.bruno.gestaovagas.exceptions;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Company não existe.");
    }
}
