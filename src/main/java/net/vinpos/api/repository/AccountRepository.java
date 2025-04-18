package net.vinpos.api.repository;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.Account;

public interface AccountRepository extends BaseRepository<Account, UUID> {
  Optional<Account> findByUsernameAndPassword(String username, String password);
}
