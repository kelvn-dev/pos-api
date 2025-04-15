package net.vinpos.api.service.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import net.vinpos.api.dto.rest.request.OrderItemReqDto;
import net.vinpos.api.dto.rest.request.OrderReqDto;
import net.vinpos.api.enums.OrderStatus;
import net.vinpos.api.exception.BadRequestException;
import net.vinpos.api.mapping.rest.OrderItemMapper;
import net.vinpos.api.mapping.rest.OrderMapper;
import net.vinpos.api.model.Dish;
import net.vinpos.api.model.Order;
import net.vinpos.api.model.OrderItem;
import net.vinpos.api.model.Shift;
import net.vinpos.api.repository.OrderRepository;
import net.vinpos.api.repository.ShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService extends BaseService<Order, OrderRepository> {

  private final OrderMapper orderMapper;
  private final OrderItemMapper orderItemMapper;
  private final DishService dishService;
    private final ShiftRepository shiftRepository;
  @PersistenceContext private final EntityManager entityManager;

  public OrderService(
          OrderRepository repository,
          OrderMapper orderMapper,
          OrderItemMapper orderItemMapper,
          DishService dishService, ShiftRepository shiftRepository,
          EntityManager entityManager) {
    super(repository);
    this.orderMapper = orderMapper;
    this.orderItemMapper = orderItemMapper;
    this.dishService = dishService;
      this.shiftRepository = shiftRepository;
      this.entityManager = entityManager;
  }

  @Transactional
  public Order create(OrderReqDto dto) {
    Order order = orderMapper.dto2Model(dto);
    order.setStatus(OrderStatus.PENDING);
    Set<OrderItem> orderItems = new HashSet<>();
    dto.getOrderItems()
        .forEach(
            item -> {
              Dish dish = dishService.getById(item.getDishId(), false);
              OrderItem orderItem = orderItemMapper.dto2Model(item, dish);
              orderItems.add(orderItem);
            });

    order.setOrderItems(orderItems);
    return entityManager.merge(order);
  }

  @Transactional
  public Order updateById(UUID id, OrderReqDto dto) {
    Order order = this.getById(id, false);
    if (!order.getStatus().equals(OrderStatus.PENDING)) {
      throw new BadRequestException(
          String.format(
              "Order no longer accept changes, the current status is %s", order.getStatus()));
    }
    Set<OrderItem> orderItems = order.getOrderItems();
    Set<OrderItemReqDto> orderItemReqDtos = dto.getOrderItems();

    Set<UUID> currentDishIds =
        orderItems.stream().map(OrderItem::getDishId).collect(Collectors.toSet());
    Set<UUID> requestedDishIds =
        orderItemReqDtos.stream().map(OrderItemReqDto::getDishId).collect(Collectors.toSet());

    List<UUID> dishIdToAdd =
        requestedDishIds.stream().filter(dishId -> !currentDishIds.contains(dishId)).toList();

    List<UUID> dishIdToRemove =
        currentDishIds.stream().filter(dishId -> !requestedDishIds.contains(dishId)).toList();

    removeOrderItemOnUpdatingOrder(orderItems, dishIdToRemove);
    updateOrderItemOnUpdatingOrder(orderItems, orderItemReqDtos);
    addOrderItemOnUpdatingOrder(orderItems, orderItemReqDtos, dishIdToAdd);

    return repository.save(order);
  }

  private void removeOrderItemOnUpdatingOrder(
      Set<OrderItem> orderItems, List<UUID> dishIdToRemove) {
    orderItems.removeIf(e -> dishIdToRemove.contains(e.getDishId()));
  }

  private void updateOrderItemOnUpdatingOrder(
      Set<OrderItem> orderItems, Set<OrderItemReqDto> orderItemReqDtos) {
    orderItems.forEach(
        item -> {
          OrderItemReqDto orderItemReqDto =
              orderItemReqDtos.stream()
                  .filter(itemDto -> itemDto.getDishId().equals(item.getDishId()))
                  .findFirst()
                  .orElse(null);
          if (Objects.nonNull(orderItemReqDto)) {
            orderItemMapper.updateModelFromDto(orderItemReqDto, item);
          }
        });
  }

  private void addOrderItemOnUpdatingOrder(
      Set<OrderItem> orderItems, Set<OrderItemReqDto> orderItemReqDtos, List<UUID> dishIdToAdd) {
    List<Dish> dishes = dishService.getAllById(dishIdToAdd);
    Set<OrderItemReqDto> orderItemToAdd =
        orderItemReqDtos.stream()
            .filter(e -> dishIdToAdd.contains(e.getDishId()))
            .collect(Collectors.toSet());
    orderItemToAdd.forEach(
        item -> {
          Dish dish =
              dishes.stream()
                  .filter(e -> e.getId().equals(item.getDishId()))
                  .findFirst()
                  .orElse(null);
          OrderItem orderItem = orderItemMapper.dto2Model(item, dish);
          orderItems.add(orderItem);
        });
  }

  public List<Order> getBySessionId(String sessionId) {
    return repository.getBySessionId(sessionId);
  }

  public void updateStatusById(UUID id, OrderStatus orderStatus) {
    Order order = getById(id, false);
    order.setStatus(orderStatus);
    repository.save(order);
  }

  public List<Order> getList(Long startTime, Long endTime) {
    return repository.getAllInStartEndTimeShift(startTime, endTime);
  }
}
