package cloud.matheusdcunha.gerenciamento_projetos.controller;

import cloud.matheusdcunha.gerenciamento_projetos.criteria.TaskFilterCriteria;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskPriority;
import cloud.matheusdcunha.gerenciamento_projetos.domain.enums.TaskStatus;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestStatusUpdateDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> save(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {

        TaskResponseDTO taskCreated = taskService.create(taskRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody TaskRequestStatusUpdateDTO taskRequestStatusUpdateDTO) {
        TaskResponseDTO taskUpdated = taskService.updateStatus(id, taskRequestStatusUpdateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStatus(@PathVariable long id) {
        taskService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String projectName
            ) {

        TaskFilterCriteria criteria = new TaskFilterCriteria(status, priority, projectId, projectName);

        List<TaskResponseDTO> tasks = taskService.findAll(criteria);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}

