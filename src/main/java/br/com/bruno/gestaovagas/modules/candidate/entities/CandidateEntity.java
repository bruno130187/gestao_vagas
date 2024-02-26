package br.com.bruno.gestaovagas.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "candidate")
@Data
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "João da Silva")
    private String name;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O Campo (username) não pode conter espaços.")
    @Schema(example = "joao.silva")
    private String username;

    @Email(message = "O Campo (email) deve conter um elemento válido.")
    @Schema(example = "joao.silva@email.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "\\S+", message = "O Campo (password) não pode conter espaços.")
    @Length(min = 6, max = 255, message = "O Campo (password) deve ter no mínimo 6 caracteres.")
    @Schema(example = "101010", minLength = 6, maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(example = "Gosto de desenvolver em Java e Javascript")
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
