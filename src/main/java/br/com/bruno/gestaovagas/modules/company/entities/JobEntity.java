package br.com.bruno.gestaovagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(example = "Vaga de desenvolvedor JAVA Pleno")
    private String description;
    @Schema(example = "CLT, vale alimentação, vale refeição, plano de saúde, uma folga por mês")
    private String benefits;
    @NotBlank(message = "O Campo (Nível) é obrigatório.")
    @Schema(example = "Júnior")
    private String level;
    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity companyEntity;
    @Column(name = "company_id")
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID companyId;
    @CreationTimestamp
    @Schema(example = "2024-02-13 15:42:48.443377")
    private LocalDateTime createdAt;
}
