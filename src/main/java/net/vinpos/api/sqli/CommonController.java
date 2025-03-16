package net.vinpos.api.sqli;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Common for SQLi")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sqli")
public class CommonController {

  private final AccountRepository accountRepository;
  private final BookRepository bookRepository;
  @PersistenceContext private final EntityManager entityManager;

  @PostMapping("/v1/accounts/sign-up")
  public ResponseEntity<?> signupAccount(@Valid @RequestBody AccountDto dto) {
    Account account = new Account(dto.getUsername(), dto.getPassword());
    account = accountRepository.save(account);
    return ResponseEntity.ok(account.getId());
  }

  @PostMapping("/v1/books")
  public ResponseEntity<?> createBook(@Valid @RequestBody BookDto dto) {
    Book book = new Book(dto.getName(), dto.getAuthor());
    return ResponseEntity.ok(bookRepository.save(book));
  }
}
