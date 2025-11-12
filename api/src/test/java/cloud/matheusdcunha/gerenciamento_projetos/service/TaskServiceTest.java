package cloud.matheusdcunha.gerenciamento_projetos.service;

import cloud.matheusdcunha.gerenciamento_projetos.criteria.TaskFilterCriteria;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestStatusUpdateDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.exceptions.ProjectNotFoundException;
import cloud.matheusdcunha.gerenciamento_projetos.exceptions.TaskNotFoundException;
import cloud.matheusdcunha.gerenciamento_projetos.mapper.TaskMapper;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import cloud.matheusdcunha.gerenciamento_projetos.repository.TaskRepository;
import cloud.matheusdcunha.gerenciamento_projetos.specification.TaskSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve criar uma task e retornar TaskResponseDTO quando o projeto existe")
    void testCreateTask() {
        // Arrange
        LocalDate today = LocalDate.now();
        Long projectId = 1L;

        Project project = new Project();
        project.setId(projectId);
        project.setName("Projeto Teste");

        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "Task Teste 1",
                "Descrição da task",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                today.plusDays(7),
                projectId
        );

        Task task = new Task();
        task.setTitle("Task Teste 1");
        task.setDescription("Descrição da task");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        task.setDueDate(today.plusDays(7));
        task.setProject(project);

        Task taskSaved = new Task();
        taskSaved.setId(1L);
        taskSaved.setTitle("Task Teste 1");
        taskSaved.setDescription("Descrição da task");
        taskSaved.setStatus(TaskStatus.TODO);
        taskSaved.setPriority(TaskPriority.HIGH);
        taskSaved.setDueDate(today.plusDays(7));
        taskSaved.setProject(project);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(
                1L,
                "Task Teste 1",
                "Descrição da task",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                today.plusDays(7),
                projectId
        );

        when(projectRepository.existsById(projectId)).thenReturn(true);
        when(projectRepository.getReferenceById(projectId)).thenReturn(project);
        when(taskMapper.toEntity(taskRequestDTO, project)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(taskSaved);
        when(taskMapper.toResponseDTO(taskSaved)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.create(taskRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Task Teste 1", result.title());
        assertEquals(TaskStatus.TODO, result.status());
        assertEquals(TaskPriority.HIGH, result.priority());

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).getReferenceById(projectId);
        verify(taskMapper, times(1)).toEntity(taskRequestDTO, project);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toResponseDTO(taskSaved);
    }

    @Test
    @DisplayName("Deve lançar ProjectNotFoundException quando o projeto não existe ao criar task")
    void testCreateTaskWithNonExistentProject() {
        // Arrange
        Long projectId = 999L;

        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(
                "Task Teste",
                "Descrição",
                TaskStatus.TODO,
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(5),
                projectId
        );

        when(projectRepository.existsById(projectId)).thenReturn(false);

        // Act & Assert
        assertThrows(ProjectNotFoundException.class, () -> {
            taskService.create(taskRequestDTO);
        });

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, never()).getReferenceById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve atualizar o status de uma task e retornar TaskResponseDTO")
    void testUpdateStatus() {
        // Arrange
        Long taskId = 1L;
        LocalDate today = LocalDate.now();

        Project project = new Project();
        project.setId(1L);

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Task Teste");
        task.setDescription("Descrição");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDueDate(today);
        task.setProject(project);

        TaskRequestStatusUpdateDTO statusUpdateDTO = new TaskRequestStatusUpdateDTO(TaskStatus.DONE);

        Task taskUpdated = new Task();
        taskUpdated.setId(taskId);
        taskUpdated.setTitle("Task Teste");
        taskUpdated.setDescription("Descrição");
        taskUpdated.setStatus(TaskStatus.DONE);
        taskUpdated.setPriority(TaskPriority.MEDIUM);
        taskUpdated.setDueDate(today);
        taskUpdated.setProject(project);

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(
                taskId,
                "Task Teste",
                "Descrição",
                TaskStatus.DONE,
                TaskPriority.MEDIUM,
                today,
                1L
        );

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(taskUpdated);
        when(taskMapper.toResponseDTO(taskUpdated)).thenReturn(taskResponseDTO);

        // Act
        TaskResponseDTO result = taskService.updateStatus(taskId, statusUpdateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(TaskStatus.DONE, result.status());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).toResponseDTO(taskUpdated);
    }

    @Test
    @DisplayName("Deve lançar TaskNotFoundException quando a task não existe ao atualizar status")
    void testUpdateStatusWithNonExistentTask() {
        // Arrange
        Long taskId = 999L;
        TaskRequestStatusUpdateDTO statusUpdateDTO = new TaskRequestStatusUpdateDTO(TaskStatus.DONE);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateStatus(taskId, statusUpdateDTO);
        });

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve excluir uma task pelo ID")
    void testDeleteById() {
        // Arrange
        Long taskId = 1L;

        // Act
        taskService.deleteById(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    @DisplayName("Deve retornar uma lista de TaskResponseDTO quando houver tasks")
    void testFindAll() {
        // Arrange
        LocalDate today = LocalDate.now();

        Project project = new Project();
        project.setId(1L);
        project.setName("Projeto Teste");

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setDescription("Descrição 1");
        task1.setStatus(TaskStatus.TODO);
        task1.setPriority(TaskPriority.HIGH);
        task1.setDueDate(today);
        task1.setProject(project);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setDescription("Descrição 2");
        task2.setStatus(TaskStatus.DOING);
        task2.setPriority(TaskPriority.MEDIUM);
        task2.setDueDate(today.plusDays(3));
        task2.setProject(project);

        List<Task> tasksFromRepo = List.of(task1, task2);

        TaskResponseDTO dto1 = new TaskResponseDTO(
                1L,
                "Task 1",
                "Descrição 1",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                today,
                1L
        );

        TaskResponseDTO dto2 = new TaskResponseDTO(
                2L,
                "Task 2",
                "Descrição 2",
                TaskStatus.DOING,
                TaskPriority.MEDIUM,
                today.plusDays(3),
                1L
        );

        TaskFilterCriteria criteria = new TaskFilterCriteria(null, null, null, null);

        when(taskRepository.findAll(any(Specification.class))).thenReturn(tasksFromRepo);
        when(taskMapper.toResponseDTO(task1)).thenReturn(dto1);
        when(taskMapper.toResponseDTO(task2)).thenReturn(dto2);

        // Act
        List<TaskResponseDTO> result = taskService.findAll(criteria);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).title());
        assertEquals("Task 2", result.get(1).title());

        verify(taskRepository, times(1)).findAll(any(Specification.class));
        verify(taskMapper, times(1)).toResponseDTO(task1);
        verify(taskMapper, times(1)).toResponseDTO(task2);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver tasks")
    void testFindAllEmpty() {
        // Arrange
        TaskFilterCriteria criteria = new TaskFilterCriteria(null, null, null, null);

        when(taskRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act
        List<TaskResponseDTO> result = taskService.findAll(criteria);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(taskRepository, times(1)).findAll(any(Specification.class));
        verify(taskMapper, never()).toResponseDTO(any(Task.class));
    }
}
