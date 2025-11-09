package cloud.matheusdcunha.gerenciamento_projetos.infra;


import java.time.LocalDateTime;
import java.util.List;

public record RestValidationExceptionMessage(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message ,
        String path,
        List<FieldValidationError> errors
) {
    RestValidationExceptionMessage(Integer status, String error, String path, List<FieldValidationError> erros){
        this(LocalDateTime.now(), status, error, "Validation Error", path, erros);
    }
}
