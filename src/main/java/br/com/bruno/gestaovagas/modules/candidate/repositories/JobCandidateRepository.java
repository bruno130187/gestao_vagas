package br.com.bruno.gestaovagas.modules.candidate.repositories;

import br.com.bruno.gestaovagas.modules.candidate.entities.JobCandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobCandidateRepository extends JpaRepository<JobCandidateEntity, UUID> {

    Optional<JobCandidateEntity> findByCandidateIdAndJobId(UUID candidateId, UUID jobId);

}
