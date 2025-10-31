package cloud.matheusdcunha.gerenciamento_projetos.repository;

import cloud.matheusdcunha.gerenciamento_projetos.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
