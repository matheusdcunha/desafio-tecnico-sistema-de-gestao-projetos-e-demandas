package cloud.matheusdcunha.gerenciamento_projetos.repository;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
