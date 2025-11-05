package cloud.matheusdcunha.gerenciamento_projetos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjectRequestDTO(

        @NotBlank(message = "Project must have a name")
        @Size(min = 3, max = 100, message = "Project name must be between 3 and  100 characters")
        String name,
        String description,

        @NotNull(message = "Project must have a start date")
        LocalDate startDate,
        LocalDate endDate
) {
}
