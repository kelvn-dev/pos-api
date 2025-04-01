package net.vinpos.api.sqli;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.repository.BaseRepository;

public interface AccountRepository extends BaseRepository<Account, UUID> {
  Optional<Account> findByUsernameAndPassword(String username, String password);
}
