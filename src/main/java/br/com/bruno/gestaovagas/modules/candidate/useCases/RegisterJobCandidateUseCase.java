package br.com.bruno.gestaovagas.modules.candidate.useCases;

import br.com.bruno.gestaovagas.exceptions.JobNotFoundException;
import br.com.bruno.gestaovagas.exceptions.UserNotFoundException;
import br.com.bruno.gestaovagas.modules.candidate.entities.JobCandidateEntity;
import br.com.bruno.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.bruno.gestaovagas.modules.candidate.repositories.JobCandidateRepository;
import br.com.bruno.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    //ID do Candidato
    //ID da Vaga
    public JobCandidateEntity execute(UUID idCandidato, UUID idJob) {
        //Validar se Candidato Existe

        this.candidateRepository.findById(idCandidato)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        //Validar se a vaga existe

        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        //Candidato se inscrever na vaga

        var registerJobCandidate = JobCandidateEntity.builder()
                .candidateId(idCandidato)
                .jobId(idJob)
                .build();

        var registeredJobCandidate = jobCandidateRepository.save(registerJobCandidate);

        return registeredJobCandidate;
    }

}
