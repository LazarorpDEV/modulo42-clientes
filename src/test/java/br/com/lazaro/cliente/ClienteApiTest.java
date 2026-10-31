package br.com.lazaro.cliente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteApiTest {
 @Autowired TestRestTemplate http;
 @Autowired ClienteRepository repository;
 final String path = "/api/clientes";
 final String json = "{\"nome\": \"Lazaro\", \"email\": \"lazaro@example.com\", \"cpf\": \"12345678901\"}";
 @BeforeEach void limpar() { repository.deleteAll(); }
 HttpEntity<String> body(String json) {
  HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
  return new HttpEntity<>(json, headers);
 }
 String criar() { return http.postForEntity(path, body(json), Map.class).getHeaders().getLocation().toString(); }
 @Test void cadastraEPersiste() {
  var result = http.postForEntity(path, body(json), Map.class);
  assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  assertThat(result.getHeaders().getLocation()).isNotNull();
  assertThat(result.getBody()).containsEntry("nome", "Lazaro");
  assertThat(repository.count()).isEqualTo(1);
 }
 @Test void buscaPorId() {
  var result = http.getForEntity(criar(), Map.class);
  assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
  assertThat(result.getBody()).containsEntry("nome", "Lazaro");
 }
 @Test void alteraEPersiste() {
  String uri = criar();
  var result = http.exchange(uri, HttpMethod.PUT, body("{\"nome\": \"Atualizado\", \"email\": \"lazaro@example.com\", \"cpf\": \"12345678901\"}"), Map.class);
  assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
  assertThat(http.getForObject(uri, Map.class)).containsEntry("nome", "Atualizado");
  assertThat(repository.count()).isEqualTo(1);
 }
 @Test void pesquisaPorNomeELista() {
  criar();
  assertThat(http.getForObject(path, Object[].class)).hasSize(1);
  assertThat(http.getForObject(path + "?nome=laz", Object[].class)).hasSize(1);
  assertThat(http.getForObject(path + "?nome=inexistente", Object[].class)).isEmpty();
 }
 @Test void rejeitaNomeVazio() {
  assertThat(http.postForEntity(path, body("{\"nome\": \"\", \"email\": \"lazaro@example.com\", \"cpf\": \"12345678901\"}"), String.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  assertThat(repository.count()).isZero();
 }
 @Test void rejeitaCampoInvalido() {
  assertThat(http.postForEntity(path, body("{\"nome\": \"Lazaro\", \"email\": \"invalido\", \"cpf\": \"12345678901\"}"), String.class).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
 }
 @Test void rejeitaDuplicado() {
  criar();
  assertThat(http.postForEntity(path, body(json), String.class).getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
 }
 @Test void retorna404ParaBuscaEAlteracaoInexistente() {
  assertThat(http.getForEntity(path + "/999999", String.class).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  assertThat(http.exchange(path + "/999999", HttpMethod.PUT, body(json), String.class).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
 }
}
