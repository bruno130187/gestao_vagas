package br.com.bruno.gestaovagas.exceptions;

public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException() {
        super("Vaga não existe.");
    }
}
