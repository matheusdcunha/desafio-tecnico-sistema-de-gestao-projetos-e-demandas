package cloud.matheusdcunha.gerenciamento_projetos.mapper;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.ProjectResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO toResponseDTO(Project project);
}
