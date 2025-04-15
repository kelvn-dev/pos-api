package net.vinpos.api.mapping.rest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import net.vinpos.api.dto.rest.request.InvoiceReqDto;
import net.vinpos.api.dto.rest.response.InvoiceResDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.model.Invoice;
import net.vinpos.api.model.Order;
import net.vinpos.api.model.User;
import net.vinpos.api.repository.OrderRepository;
import net.vinpos.api.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

  Invoice dto2Model(InvoiceReqDto dto);

  @Mapping(source = "cashier.nickname", target = "cashierName")
  @Mapping(target = "createdAt", expression = "java(formatTimestamp(invoice.getCreatedAt()))")
  InvoiceResDto model2Dto(Invoice invoice);

  void updateModelFromDto(InvoiceReqDto dto, @MappingTarget Invoice dish);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
          source = "content",
          target = "items",
          defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<InvoiceResDto> model2Dto(Page<Invoice> page);

  default Order map(UUID orderId, OrderRepository orderRepository) {
    if (orderId == null) return null;
    return orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
  }

  default User map(UUID userId, UserRepository userRepository) {
    if (userId == null) return null;
    return userRepository
            .findById(String.valueOf(userId))
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
  }

  // Hàm hỗ trợ định dạng timestamp
  default String formatTimestamp(long epochSeconds) {
    if (epochSeconds == 0) {
      return null;
    }
    return DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
            .format(
                    Instant.ofEpochSecond(epochSeconds)
                            .atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
                            .toLocalDateTime());
  }
}