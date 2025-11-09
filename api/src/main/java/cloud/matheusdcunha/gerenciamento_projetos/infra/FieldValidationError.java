package cloud.matheusdcunha.gerenciamento_projetos.infra;

public record FieldValidationError(
        String field,
        String message
) {
}
