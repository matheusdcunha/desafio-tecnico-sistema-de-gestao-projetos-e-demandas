package cloud.matheusdcunha.gerenciamento_projetos.mapper;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskRequestDTO;
import cloud.matheusdcunha.gerenciamento_projetos.dto.TaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper {

    @Mapping(target = "id", ignore=true)
    @Mapping(source = "taskRequestDTO.description", target = "description")
    Task toEntity(TaskRequestDTO taskRequestDTO, Project project);

    @Mapping(source = "project.id", target = "projectId")
    TaskResponseDTO toResponseDTO(Task task);

}
