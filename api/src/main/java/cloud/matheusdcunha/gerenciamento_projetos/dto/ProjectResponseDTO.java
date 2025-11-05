package cloud.matheusdcunha.gerenciamento_projetos.dto;

import java.time.LocalDate;

public record ProjectResponseDTO(

        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
