package br.com.lazaro.cliente;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
 private final ClienteService service;
 public ClienteController(ClienteService service) { this.service = service; }
 @PostMapping
 public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody ClienteRequest request) {
  Cliente entity = service.cadastrar(request);
  return ResponseEntity.created(URI.create("/api/clientes/" + entity.getId())).body(entity);
 }
 @PutMapping("/{id}")
 public Cliente alterar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) { return service.alterar(id, request); }
 @GetMapping("/{id}")
 public Cliente buscar(@PathVariable Long id) { return service.buscar(id); }
 @GetMapping
 public List<Cliente> pesquisar(@RequestParam(defaultValue = "") String nome) { return service.pesquisar(nome); }
}
