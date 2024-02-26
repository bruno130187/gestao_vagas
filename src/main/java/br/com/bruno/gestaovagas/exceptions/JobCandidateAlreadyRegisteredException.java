package br.com.bruno.gestaovagas.exceptions;

public class JobCandidateAlreadyRegisteredException extends RuntimeException{
    public JobCandidateAlreadyRegisteredException() {
        super("Candidato já cadastrado nessa vaga.");
    }
}
