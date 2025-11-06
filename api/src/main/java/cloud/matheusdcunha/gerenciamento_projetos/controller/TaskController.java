package cloud.matheusdcunha.gerenciamento_projetos.controller;

import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestStatusUpdateDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
