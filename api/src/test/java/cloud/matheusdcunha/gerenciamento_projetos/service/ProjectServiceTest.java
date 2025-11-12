package cloud.matheusdcunha.gerenciamento_projetos.service;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectResponseDTO;
import cloud.matheusdcunha.gerenciamento_projetos.mapper.ProjectMapper;
import cloud.matheusdcunha.gerenciamento_projetos.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve retornar uma lista de DTOs de projeto quando houver projetos")
    void testFindAll1() {
        // Arrange

        LocalDate today = LocalDate.now();

        Project project1 = new Project();
        project1.setId(1L);
        project1.setName("Projeto Teste 1");
        project1.setDescription("Descrição 1");
        project1.setStartDate(today);
        project1.setEndDate(today.plusMonths(1));

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("Projeto Teste 2");
        project2.setDescription("Descrição 2");
        project2.setStartDate(today.plusDays(1));
        project2.setEndDate(today.plusMonths(2));

        List<Project> projectsFromRepo = List.of(project1, project2);

        ProjectResponseDTO dto1 = new ProjectResponseDTO(
                1L,
                "Projeto Teste 1",
                "Descrição 1",
                today,
                today.plusMonths(1)
        );

        ProjectResponseDTO dto2 = new ProjectResponseDTO(
                2L,
                "Projeto Teste 2",
                "Descrição 2",
                today.plusDays(1),
                today.plusMonths(2)
        );


        when(projectRepository.findAll()).thenReturn(projectsFromRepo);


        when(projectMapper.toResponseDTO(project1)).thenReturn(dto1);
        when(projectMapper.toResponseDTO(project2)).thenReturn(dto2);

        // Act
        List<ProjectResponseDTO> actualDtos = projectService.findAll();

        // Assert
        assertNotNull(actualDtos);
        assertEquals(2, actualDtos.size());


        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(1)).toResponseDTO(project1);
        verify(projectMapper, times(1)).toResponseDTO(project2);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver projetos")
    void testFindAll2() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ProjectResponseDTO> actualDtos = projectService.findAll();

        // Assert
        assertNotNull(actualDtos);
        assertTrue(actualDtos.isEmpty());

        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, never()).toResponseDTO(any(Project.class));
    }

    @Test
    @DisplayName("Deve criar um projeto e retornar um ProjectResponseDTO")
    void testCreateProject() {

        // Arrange
        LocalDate today = LocalDate.now();

        Project project = new Project();
        project.setId(1L);
        project.setName("Projeto Teste 1");
        project.setDescription("Descrição 1");
        project.setStartDate(today);
        project.setEndDate(today.plusMonths(1));

        ProjectRequestDTO projectRequestDto = new ProjectRequestDTO(
                "Projeto Teste 1",
                "Descrição 1",
                today,
                today.plusMonths(1)
        );

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO(
                1L,
                "Projeto Teste 1",
                "Descrição 1",
                today,
                today.plusMonths(1)
        );

        when(projectMapper.toEntity(projectRequestDto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toResponseDTO(project)).thenReturn(projectResponseDTO);


        // Act
        ProjectResponseDTO projectCreated = projectService.create(projectRequestDto);

        // Assert
        assertNotNull(projectResponseDTO);
        verify(projectMapper, times(1)).toResponseDTO(project);
        verify(projectMapper, times(1)).toResponseDTO(project);
        verify(projectRepository, times(1)).save(project);

        assertEquals(projectResponseDTO, projectCreated);

    }

    @Test
    @DisplayName("Deve excluir um projeto")
    void testDelete() {

        // Act
        projectService.delete(1L);

        // Assert
        verify(projectRepository, times(1)).deleteById(1L);
    }
}