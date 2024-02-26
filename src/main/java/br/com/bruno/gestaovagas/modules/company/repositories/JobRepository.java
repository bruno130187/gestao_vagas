package br.com.bruno.gestaovagas.modules.company.repositories;

import br.com.bruno.gestaovagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByDescriptionIsContainingIgnoreCase(String filter);

}
