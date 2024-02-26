package br.com.bruno.gestaovagas.modules.candidate.useCases;

import br.com.bruno.gestaovagas.exceptions.CandidateNotFoundException;
import br.com.bruno.gestaovagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.bruno.gestaovagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new CandidateNotFoundException();
                });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(String.valueOf(candidate.getId()))
                .name(candidate.getName())
                .build();
        return candidateDTO;
    }

}
