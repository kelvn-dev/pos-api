package net.vinpos.api.controller.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.enums.OrderStatus;
import net.vinpos.api.mapping.rest.OrderMapper;
import net.vinpos.api.model.Order;
import net.vinpos.api.service.rest.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController implements SecuredRestController {
  private final OrderService orderService;
  private final OrderMapper orderMapper;

  @PutMapping("/{id}/rejection")
  public ResponseEntity<?> rejectOrder(@PathVariable UUID id) {
    orderService.updateStatusById(id, OrderStatus.REJECTED);
    return ResponseEntity.ok(null);
  }

  @PutMapping("/{id}/approval")
  public ResponseEntity<?> approveOrder(@PathVariable UUID id) {
    orderService.updateStatusById(id, OrderStatus.APPROVED);
    return ResponseEntity.ok(null);
  }

  @PutMapping("/{id}/ready-to-deliver")
  public ResponseEntity<?> readyToDeliverOrder(@PathVariable UUID id) {
    orderService.updateStatusById(id, OrderStatus.READY_TO_DELIVER);
    return ResponseEntity.ok(null);
  }

  @PutMapping("/{id}/delivered")
  public ResponseEntity<?> deliveredOrder(@PathVariable UUID id) {
    orderService.updateStatusById(id, OrderStatus.DELIVERED);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Order order = orderService.getById(id, false);
    return ResponseEntity.ok(orderMapper.model2Dto(order));
  }

  @GetMapping
  public ResponseEntity<?> getBySessionId(@RequestParam(name = "session-id") String sessionId) {
    List<Order> orders = orderService.getBySessionId(sessionId);
    return ResponseEntity.ok(orderMapper.model2Dto(orders));
  }
}
