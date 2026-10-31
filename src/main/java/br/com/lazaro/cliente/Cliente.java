package br.com.lazaro.cliente;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "clientes")
public class Cliente {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @Column(nullable = false, length = 120)
 private String nome;
 @Column(nullable = false, length = 160)
 private String email;
 @Column(nullable = false, unique = true, length = 11)
 private String cpf;
 protected Cliente() {}
 public Cliente(ClienteRequest request) { atualizar(request); }
 public void atualizar(ClienteRequest request) {
  this.nome = request.nome().strip();
  this.email = request.email().strip();
  this.cpf = request.cpf().strip();
 }
 public Long getId() { return id; }
 public String getNome() { return nome; }
 public String getEmail() { return email; }
 public String getCpf() { return cpf; }
}
