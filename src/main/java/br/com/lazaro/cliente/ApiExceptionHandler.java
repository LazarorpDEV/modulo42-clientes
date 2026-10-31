package br.com.lazaro.cliente;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ProblemDetail conflito(DataIntegrityViolationException ex) {
  return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Registro duplicado ou dados incompatíveis com o banco");
 }
 @Override
 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
  ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, "Confira os campos enviados");
  problem.setProperty("erros", ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList());
  return handleExceptionInternal(ex, problem, headers, status, request);
 }
}
