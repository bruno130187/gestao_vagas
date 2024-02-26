package br.com.bruno.gestaovagas.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O Campo (username) não pode conter espaços.")
    private String username;
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O Campo (password) não pode conter espaços.")
    @Length(min = 6, max = 255, message = "O Campo (password) deve ter no mínimo 6 caracteres.")
    private String password;
    @Email(message = "O Campo (email) deve conter um elemento válido.")
    private String email;
    private String website;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
