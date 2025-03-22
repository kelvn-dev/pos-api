package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.dto.rest.request.OrderReqDto;
import net.vinpos.api.mapping.rest.OrderMapper;
import net.vinpos.api.model.Order;
import net.vinpos.api.service.rest.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer/orders")
@RequiredArgsConstructor
public class CustomerOrderController {
  private final OrderService orderService;
  private final OrderMapper orderMapper;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody @Valid OrderReqDto dto) {
    orderService.create(dto);
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

  @PutMapping("/{id}")
  public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody @Valid OrderReqDto dto) {
    orderService.updateById(id, dto);
    return ResponseEntity.ok(null);
  }
}
