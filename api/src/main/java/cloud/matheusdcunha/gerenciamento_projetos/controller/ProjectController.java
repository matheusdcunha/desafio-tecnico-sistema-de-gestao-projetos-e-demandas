package cloud.matheusdcunha.gerenciamento_projetos.controller;

import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import cloud.matheusdcunha.gerenciamento_projetos.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> findAll() {
        List<ProjectResponseDTO> projects = projectService.findAll();

        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> save(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {

        ProjectResponseDTO projectCreated = projectService.create(projectRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectCreated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
