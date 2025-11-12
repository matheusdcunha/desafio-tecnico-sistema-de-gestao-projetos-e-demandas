package cloud.matheusdcunha.gerenciamento_projetos.mapper;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectResponseDTO;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-12T20:14:34-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toEntity(ProjectRequestDTO projectRequestDTO) {
        if ( projectRequestDTO == null ) {
            return null;
        }

        Project project = new Project();

        project.setName( projectRequestDTO.name() );
        project.setDescription( projectRequestDTO.description() );
        project.setStartDate( projectRequestDTO.startDate() );
        project.setEndDate( projectRequestDTO.endDate() );

        return project;
    }

    @Override
    public ProjectResponseDTO toResponseDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        id = project.getId();
        name = project.getName();
        description = project.getDescription();
        startDate = project.getStartDate();
        endDate = project.getEndDate();

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO( id, name, description, startDate, endDate );

        return projectResponseDTO;
    }
}
