package net.vinpos.api.sqli;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Inferential (Blind) SQLi")
@RestController
@RequiredArgsConstructor
public class InferentialSQLiController {

  @PersistenceContext private final EntityManager entityManager;

  /**
   * Boolean-based SQL Injection
   * @param author
   * @return
   */
  @GetMapping("/vulnerable/boolean-based/books")
  @Operation(summary = "Boolean-based SQL injection", description = "" +
          "Input these to param author: admin' OR '1'='1' + admin' OR '1'='0'. " +
          "If the application behaves differently in each case, it is susceptible to boolean-based blind. " +
          "Iterate this to discover the full name of the first table in the database structure: admin' AND (\n" +
          "  SELECT substring(table_name FROM 2 FOR 1)\n" +
          "  FROM information_schema.tables\n" +
          "  WHERE table_schema = 'public'\n" +
          "  ORDER BY table_name\n" +
          "  LIMIT 1\n" +
          ") = 'c' --")
  public ResponseEntity<?> vulnerableBooleanBasedGetBook(@RequestParam String author) {
    String sql = String.format("SELECT * FROM book where author = '%s'", author);
    Query query = entityManager.createNativeQuery(sql, Book.class);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }

//  /**
//   * Time-based SQL Injection
//   * @param author
//   * @return
//   */
//  @GetMapping("/vulnerable/time-based/books")
//  @Operation(summary = "Time-based SQL injection", description = "Input this to param author for account retrieval: admin' UNION SELECT * FROM account --")
//  public ResponseEntity<?> vulnerableTimeBasedGetBook(@RequestParam String author) {
//    String sql = String.format("SELECT * FROM book where author = '%s'", author);
//    Query query = entityManager.createNativeQuery(sql, Book.class);
//    List<Book> books = query.getResultList();
//    return ResponseEntity.ok(books);
//  }
}
