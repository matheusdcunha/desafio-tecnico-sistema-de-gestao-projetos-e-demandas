package cloud.matheusdcunha.gerenciamento_projetos.domain;

import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task must have a title")
    @Column(name="title", nullable = false)
    @Size(min = 5, max = 150,  message = "Task title must be between 5 and 150 characters")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Task must have a status")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @NotNull(message = "Task must have a priority")
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 20)
    private TaskPriority priority;

    @Column(name= "due_date", length = 20)
    private LocalDate dueDate;

    @NotNull(message = "Task must in a project")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", nullable = false)
    private Project project;

}
