package net.vinpos.api.repository;

import java.util.List;
import java.util.UUID;
import net.vinpos.api.model.Order;

public interface OrderRepository extends BaseRepository<Order, UUID> {
  List<Order> getBySessionId(String sessionId);
}
