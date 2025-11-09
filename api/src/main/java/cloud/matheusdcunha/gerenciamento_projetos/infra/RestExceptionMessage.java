package cloud.matheusdcunha.gerenciamento_projetos.infra;

import java.time.LocalDateTime;

public record RestExceptionMessage(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
    public RestExceptionMessage(Integer status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path);
    }
}