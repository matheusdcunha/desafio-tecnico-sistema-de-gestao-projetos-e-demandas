package cloud.matheusdcunha.gerenciamento_projetos.mapper;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-05T22:15:38-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toEntity(TaskRequestDTO taskRequestDTO, Project project) {
        if ( taskRequestDTO == null && project == null ) {
            return null;
        }

        Task task = new Task();

        if ( taskRequestDTO != null ) {
            task.setDescription( taskRequestDTO.description() );
            task.setTitle( taskRequestDTO.title() );
            task.setStatus( taskRequestDTO.status() );
            task.setPriority( taskRequestDTO.priority() );
            task.setDueDate( taskRequestDTO.dueDate() );
        }
        task.setProject( project );

        return task;
    }

    @Override
    public TaskResponseDTO toResponseDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        Long projectId = null;
        Long id = null;
        String title = null;
        String description = null;
        TaskStatus status = null;
        TaskPriority priority = null;
        LocalDate dueDate = null;

        projectId = taskProjectId( task );
        id = task.getId();
        title = task.getTitle();
        description = task.getDescription();
        status = task.getStatus();
        priority = task.getPriority();
        dueDate = task.getDueDate();

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO( id, title, description, status, priority, dueDate, projectId );

        return taskResponseDTO;
    }

    private Long taskProjectId(Task task) {
        if ( task == null ) {
            return null;
        }
        Project project = task.getProject();
        if ( project == null ) {
            return null;
        }
        Long id = project.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
