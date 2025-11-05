package cloud.matheusdcunha.gerenciamento_projetos.service;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.mapper.ProjectMapper;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectResponseDTO> findAll() {

        List<Project> projects = projectRepository.findAll();
        List<ProjectResponseDTO> projectResponseDTOS = projects.stream().map(projectMapper::toResponseDTO).toList();

        return projectResponseDTOS;
    }

    public ProjectResponseDTO create(ProjectRequestDTO projectRequestDTO) {
        Project project = projectMapper.toEntity(projectRequestDTO);

        return projectMapper.toResponseDTO(projectRepository.save(project));
    }
}
