package br.com.lazaro.cliente;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ClienteRequest(
 @NotBlank @Size(max = 120) String nome,
 @NotBlank @Email @Size(max = 160) String email,
 @NotBlank @Pattern(regexp = "[0-9]{11}") String cpf) {}
