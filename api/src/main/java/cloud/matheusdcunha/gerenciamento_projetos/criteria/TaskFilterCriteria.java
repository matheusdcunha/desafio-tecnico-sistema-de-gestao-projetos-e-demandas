package cloud.matheusdcunha.gerenciamento_projetos.criteria;

import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;

public record TaskFilterCriteria(
        TaskStatus status,
        TaskPriority priority,
        Long projectId,
        String projectName
) {
}
