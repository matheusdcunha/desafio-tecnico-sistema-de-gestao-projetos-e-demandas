package cloud.matheusdcunha.gerenciamento_projetos.dto;

import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskRequestStatusUpdateDTO(
        @NotNull
        TaskStatus status
) {
}
