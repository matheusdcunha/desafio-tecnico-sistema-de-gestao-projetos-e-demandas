package cloud.matheusdcunha.gerenciamento_projetos.service;

import cloud.matheusdcunha.gerenciamento_projetos.criteria.TaskFilterCriteria;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestStatusUpdateDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.exceptions.ProjectNotFoundException;
import cloud.matheusdcunha.gerenciamento_projetos.exceptions.TaskNotFoundException;
import cloud.matheusdcunha.gerenciamento_projetos.mapper.TaskMapper;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import cloud.matheusdcunha.gerenciamento_projetos.repository.TaskRepository;
import cloud.matheusdcunha.gerenciamento_projetos.specification.TaskSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponseDTO create(TaskRequestDTO taskRequestDTO) {

        Long projectId = taskRequestDTO.projectId();

        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("Project not found with ID: " + projectId);
        }

        Project project = projectRepository.getReferenceById(projectId);

        Task task = taskMapper.toEntity(taskRequestDTO, project);

        Task taskCreated = taskRepository.save(task);

        return taskMapper.toResponseDTO(taskCreated);
    }

    public TaskResponseDTO updateStatus(Long id, TaskRequestStatusUpdateDTO taskRequestStatusUpdateDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        task.setStatus(taskRequestStatusUpdateDTO.status());

        Task taskUpdated = taskRepository.save(task);
        return taskMapper.toResponseDTO(taskUpdated);
    }

    public void deleteById(long id) {

        taskRepository.deleteById(id);

    }

    public List<TaskResponseDTO> findAll(TaskFilterCriteria criteria){

        List<Task> tasksEntity = taskRepository.findAll(TaskSpecification.build(criteria));

        return tasksEntity.stream().map(taskMapper::toResponseDTO).toList();
    }

}
