package net.vinpos.api.repository;

import net.vinpos.api.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends BaseRepository<User, String> {

  List<User> findAllByMembershipExpiryDateBefore(LocalDateTime now);
}
