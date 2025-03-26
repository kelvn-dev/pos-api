package net.vinpos.api.controller.rest;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.enums.OrderStatus;
import net.vinpos.api.service.rest.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderController implements SecuredRestController {
  private final OrderService orderService;

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
}
