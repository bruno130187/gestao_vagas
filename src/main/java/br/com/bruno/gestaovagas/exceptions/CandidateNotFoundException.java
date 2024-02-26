package br.com.bruno.gestaovagas.exceptions;

public class CandidateNotFoundException extends RuntimeException{
    public CandidateNotFoundException() {
        super("Candidato não existe.");
    }
}
