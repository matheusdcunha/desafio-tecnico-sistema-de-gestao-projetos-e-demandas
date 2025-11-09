package cloud.matheusdcunha.gerenciamento_projetos.infra;

import cloud.matheusdcunha.gerenciamento_projetos.exceptions.TaskNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<RestExceptionMessage> entityNotFoundException(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = request.getDescription(false).replace("uri=", "");

        RestExceptionMessage message = new RestExceptionMessage(status.value(), status.getReasonPhrase(), exception.getMessage(), path);

        return ResponseEntity.status(status).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        status = HttpStatus.BAD_REQUEST;

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        String path = request.getDescription(false).replace("uri=", "");

        List<FieldValidationError> erros = exception.getBindingResult().getAllErrors().stream().map(objectError -> {
            String fieldName = (objectError instanceof  FieldError field) ? field.getField(): objectError.getDefaultMessage();

            return new FieldValidationError(fieldName, objectError.getDefaultMessage());
        }).toList();

        RestValidationExceptionMessage message = new RestValidationExceptionMessage(httpStatus.value(), httpStatus.getReasonPhrase(), path, erros);

        return ResponseEntity.status(httpStatus).body(message);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        status = HttpStatus.NOT_FOUND;

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        String path = request.getDescription(false).replace("uri=", "");

        RestExceptionMessage message = new RestExceptionMessage(httpStatus.value(), httpStatus.getReasonPhrase(), "Route not exists", path);

        return ResponseEntity.status(httpStatus).body(message);
    }

}

