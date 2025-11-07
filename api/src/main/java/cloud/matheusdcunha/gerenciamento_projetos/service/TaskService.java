package cloud.matheusdcunha.gerenciamento_projetos.service;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestStatusUpdateDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.mapper.TaskMapper;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import cloud.matheusdcunha.gerenciamento_projetos.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    final TaskRepository taskRepository;
    final ProjectRepository projectRepository;
    final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }


    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {

        Long projectId = taskRequestDTO.projectId();

        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with ID: " + projectId);
        }

        Project project = projectRepository.getReferenceById(projectId);

        Task task = taskMapper.toEntity(taskRequestDTO, project);

        Task taskCreated = taskRepository.save(task);

        return taskMapper.toResponseDTO(taskCreated);
    }

    public TaskResponseDTO updateStatus(long id, TaskRequestStatusUpdateDTO taskRequestStatusUpdateDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        task.setStatus(taskRequestStatusUpdateDTO.status());

        Task taskUpdated = taskRepository.save(task);
        return taskMapper.toResponseDTO(taskUpdated);
    }

    public void deleteById(long id) {

        taskRepository.deleteById(id);

    }

}
