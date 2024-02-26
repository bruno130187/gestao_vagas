package br.com.bruno.gestaovagas.modules.candidate.controllers;

import br.com.bruno.gestaovagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.bruno.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.bruno.gestaovagas.modules.candidate.useCases.*;
import br.com.bruno.gestaovagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/candidate")
@Tag(name = "Candidato", description = "Informações do Candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private RegisterJobCandidateUseCase registerJobCandidateUseCase;

    @Autowired
    private CheckIfAlreadyRegisteredCandidateJobUseCase checkIfAlreadyRegisteredCandidateJobUseCase;

    @PostMapping(path = "/")
    @Operation(summary = "Faz o cadastro de um novo candidato.", description = "Esse endpoint é responsável por criar um novo candidato.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = CandidateEntity.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Usuário já existe."
            )
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Traz as informações do candidato.", description = "Esse endpoint é responsável por mostrar as informações do candidato.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
                            )
                    }
            )
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest httpServletRequest) {
        var idCandidate = httpServletRequest.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping(path = "/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Esse endpoint é responsável por listar todas as vagas disponíveis para os candidatos autenticados baseados no filtro.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = JobEntity.class
                                            )
                                    )
                            )
                    }
            )
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping(path = "/job/registerCandidate")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Registra um candidato em uma vaga", description = "Esse endpoint é responsável por registrar um candidato autenticado em uma vaga.")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> registerCandidateJob(HttpServletRequest httpServletRequest, @RequestBody UUID idJob) {
        var idCandidate = httpServletRequest.getAttribute("candidate_id");
        try {
            this.checkIfAlreadyRegisteredCandidateJobUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            var result = this.registerJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
