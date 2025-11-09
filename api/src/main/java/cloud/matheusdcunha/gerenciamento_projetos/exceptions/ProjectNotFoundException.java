package cloud.matheusdcunha.gerenciamento_projetos.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ProjectNotFoundException extends EntityNotFoundException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
