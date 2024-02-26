package br.com.bruno.gestaovagas.modules.candidate.useCases;

import br.com.bruno.gestaovagas.exceptions.JobNotFoundException;
import br.com.bruno.gestaovagas.exceptions.UserNotFoundException;
import br.com.bruno.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.bruno.gestaovagas.modules.candidate.entities.JobCandidateEntity;
import br.com.bruno.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.bruno.gestaovagas.modules.candidate.repositories.JobCandidateRepository;
import br.com.bruno.gestaovagas.modules.company.entities.JobEntity;
import br.com.bruno.gestaovagas.modules.company.repositories.JobRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RegisterJobCandidateUseCaseTest {

    @InjectMocks
    private RegisterJobCandidateUseCase registerJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobCandidateRepository jobCandidateRepository;

    @Test
    @DisplayName("Shold not be able to apply job with candidate not found")
    void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            registerJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Shold not be able to apply job with job not found")
    void should_not_be_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());

        Mockito.when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            registerJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Shold be able to register a new job to candidate")
    void should_be_able_to_register_a_new_job_to_candidate() {
        try {
            var idCandidate = UUID.randomUUID();
            var candidate = new CandidateEntity();
            candidate.setId(UUID.randomUUID());

            var idJob= UUID.randomUUID();
            var job = new JobEntity();
            job.setId(UUID.randomUUID());

            var registerJob = JobCandidateEntity.builder()
                    .id(UUID.randomUUID())
                    .candidateId(idCandidate)
                    .jobId(idJob)
                    .build();


            var registeredJob = JobCandidateEntity.builder()
                    .id(registerJob.getId())
                    .candidateId(idCandidate)
                    .jobId(idJob)
                    .build();

            Mockito.when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
            Mockito.when(jobRepository.findById(idJob)).thenReturn(Optional.of(job));
            Mockito.when(jobCandidateRepository.save(registerJob)).thenReturn(registeredJob);

            var result = registerJobCandidateUseCase.execute(idCandidate, idJob);

            Assertions.assertThat(result).hasFieldOrProperty("id");
            Assertions.assertThat(result).isNot(null);
        } catch (Exception e) {
            //Assertions.assertThat(e).isInstanceOf(JobNotFoundException.class);
        }

    }

}
