package net.vinpos.api.repository;

import java.util.Optional;
import net.vinpos.api.model.User;

public interface UserRepository extends BaseRepository<User, String> {
  Optional<User> findByEmail(String email);
}
