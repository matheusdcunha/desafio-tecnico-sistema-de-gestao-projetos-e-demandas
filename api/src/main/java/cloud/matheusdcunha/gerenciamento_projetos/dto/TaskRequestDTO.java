package cloud.matheusdcunha.gerenciamento_projetos.dto;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequestDTO(

@NotBlank(message = "Task must have a title")
@Size(min = 5, max = 150,  message = "Task title must be between 5 and 150 characters")
String title,
String description,

@NotNull(message = "Task must have a status")
@Enumerated(EnumType.STRING)
TaskStatus status,

@NotNull(message = "Task must have a priority")
@Enumerated(EnumType.STRING)
TaskPriority priority,
LocalDate dueDate,
Long projectId
) {
}
