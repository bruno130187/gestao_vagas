package br.com.bruno.gestaovagas.modules.candidate.useCases;

import br.com.bruno.gestaovagas.exceptions.JobCandidateAlreadyRegisteredException;
import br.com.bruno.gestaovagas.exceptions.JobNotFoundException;
import br.com.bruno.gestaovagas.exceptions.UserNotFoundException;
import br.com.bruno.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.bruno.gestaovagas.modules.candidate.repositories.JobCandidateRepository;
import br.com.bruno.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckIfAlreadyRegisteredCandidateJobUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public void execute(UUID idCandidate, UUID idJob) {

        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        //Validar se Candidato já se inscreveu no curso informado

        this.jobCandidateRepository
                .findByCandidateIdAndJobId(idCandidate, idJob)
                .ifPresent(job -> {
                    throw new JobCandidateAlreadyRegisteredException();
                });

    }

}
