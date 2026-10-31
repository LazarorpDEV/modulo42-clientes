package br.com.lazaro.cliente;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;
@Service
@Transactional
public class ClienteService {
 private final ClienteRepository repository;
 public ClienteService(ClienteRepository repository) { this.repository = repository; }
 @Transactional(readOnly = true)
 public List<Cliente> pesquisar(String nome) {
  return repository.findByNomeContainingIgnoreCaseOrderByIdAsc(nome.strip());
 }
 @Transactional(readOnly = true)
 public Cliente buscar(Long id) {
  return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
 }
 public Cliente cadastrar(ClienteRequest request) {
  if (repository.existsByCpf(request.cpf().strip())) throw new ResponseStatusException(CONFLICT, "Cpf já cadastrado");
  return repository.saveAndFlush(new Cliente(request));
 }
 public Cliente alterar(Long id, ClienteRequest request) {
  Cliente entity = buscar(id);
  if (repository.existsByCpfAndIdNot(request.cpf().strip(), id)) throw new ResponseStatusException(CONFLICT, "Cpf já cadastrado");
  entity.atualizar(request);
  return repository.saveAndFlush(entity);
 }
}
