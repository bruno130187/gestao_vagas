package br.com.bruno.gestaovagas.modules.candidate.useCases;

import br.com.bruno.gestaovagas.modules.company.entities.JobEntity;
import br.com.bruno.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionIsContainingIgnoreCase(filter);
    }
}
