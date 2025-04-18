package net.vinpos.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.dto.AccountDto;
import net.vinpos.api.exception.UnauthorizedException;
import net.vinpos.api.model.Account;
import net.vinpos.api.model.Book;
import net.vinpos.api.utils.TokenGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "In-band SQLi")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sqli")
public class InBandSQLiController {

  @PersistenceContext private final EntityManager entityManager;

  /**
   * Error-based SQL Injection
   *
   * @param dto
   * @return
   */
  @PostMapping("/vulnerable/v1/accounts/sign-in")
  @Operation(
      summary = "Error-based SQL Injection",
      description = "Input this to param password for bypass authentication: admin' OR '1'='1")
  public ResponseEntity<?> vulnerableSignIn(@Valid @RequestBody AccountDto dto) {
    String sql =
        String.format(
            "SELECT * FROM account where username = '%s' and password = '%s'",
            dto.getUsername(), dto.getPassword());
    Query query = entityManager.createNativeQuery(sql, Account.class);
    List<Account> accounts = query.getResultList();
    if (accounts.isEmpty()) {
      throw new UnauthorizedException();
    }

    Map<String, String> response = new HashMap<>();
    response.put("token", TokenGenerator.generateToken());
    return ResponseEntity.ok(response);
  }

  /**
   * Defense Error-based SQL Injection
   *
   * @param dto
   * @return
   */
  @PostMapping("/v1/accounts/sign-in")
  public ResponseEntity<?> signIn(@Valid @RequestBody AccountDto dto) {
    String sql = "SELECT * FROM account where username = :username and password = :password";
    Query query = entityManager.createNativeQuery(sql, Account.class);
    query.setParameter("username", dto.getUsername());
    query.setParameter("password", dto.getPassword());
    List<Account> accounts = query.getResultList();
    if (accounts.isEmpty()) {
      throw new UnauthorizedException();
    }

    Map<String, String> response = new HashMap<>();
    response.put("token", TokenGenerator.generateToken());
    return ResponseEntity.ok(accounts);
  }

  /**
   * Union-based SQL Injection
   *
   * @param author
   * @return
   */
  @GetMapping("/vulnerable/v1/books")
  @Operation(
      summary = "Union-based SQL Injection",
      description =
          "Input this to param author for account retrieval: admin' UNION SELECT * FROM account --")
  public ResponseEntity<?> vulnerableGetBooks(@RequestParam String author) {
    String sql = String.format("SELECT * FROM book where author = '%s'", author);
    Query query = entityManager.createNativeQuery(sql, Book.class);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }

  /**
   * Defense Union-based SQL Injection
   *
   * @param author
   * @return
   */
  @GetMapping("/v1/books")
  public ResponseEntity<?> getBooks(@RequestParam String author) {
    String sql = "SELECT * FROM book where author = :author";
    Query query = entityManager.createNativeQuery(sql, Book.class);
    query.setParameter("author", author);
    List<Book> books = query.getResultList();
    return ResponseEntity.ok(books);
  }
}
