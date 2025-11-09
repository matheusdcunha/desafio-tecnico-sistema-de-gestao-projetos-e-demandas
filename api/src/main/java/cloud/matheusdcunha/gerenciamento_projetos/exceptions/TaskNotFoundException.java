package cloud.matheusdcunha.gerenciamento_projetos.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
    public TaskNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
