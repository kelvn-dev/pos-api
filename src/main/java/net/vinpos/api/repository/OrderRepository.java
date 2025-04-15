package net.vinpos.api.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.Order;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends BaseRepository<Order, UUID> {
  List<Order> getBySessionId(String sessionId);
}
