package net.vinpos.api.sqli;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Inferential (Blind) SQLi")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sqli")
public class InferentialSQLiController {

  @PersistenceContext private final EntityManager entityManager;

  /**
   * Boolean-based SQL Injection
   *
   * @param author
   * @return
   */
  @GetMapping("/vulnerable/boolean-based/books")
  @Operation(
      summary = "Boolean-based SQL injection",
      description = "Input this to param: ' OR '1'='1")
  public ResponseEntity<?> vulnerableBooleanBasedGetBook(@RequestParam String author) {
    String sql = String.format("SELECT * FROM book where author = '%s'", author);
    Query query = entityManager.createNativeQuery(sql, Book.class);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }

  /**
   * Time-based SQL Injection
   *
   * @param author
   * @return
   */
  @GetMapping("/vulnerable/time-based/books")
  @Operation(
      summary = "Time-based SQL injection",
      description = "Input this to param: ' OR EXISTS(SELECT 1 FROM pg_sleep(5))--")
  public ResponseEntity<?> vulnerableTimeBasedGetBook(@RequestParam String author) {
    String sql = String.format("SELECT * FROM book where author = '%s'", author);
    Query query = entityManager.createNativeQuery(sql, Book.class);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }
}
