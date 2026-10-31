package br.com.lazaro.cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
 List<Cliente> findByNomeContainingIgnoreCaseOrderByIdAsc(String nome);
 boolean existsByCpf(String cpf);
 boolean existsByCpfAndIdNot(String cpf, Long id);
}
