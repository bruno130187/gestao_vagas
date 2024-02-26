package br.com.bruno.gestaovagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Gosto de desenvolvimento Java e de andar de bicicleta.")
    private String description;
    @Schema(example = "bruno.araujo")
    private String username;
    @Schema(example = "bruno@email.com")
    private String email;
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private String id;
    @Schema(example = "Bruno Araújo")
    private String name;
}
