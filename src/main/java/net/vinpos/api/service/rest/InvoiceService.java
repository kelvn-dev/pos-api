package net.vinpos.api.service.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.dto.rest.request.InvoiceReqDto;
import net.vinpos.api.dto.rest.response.InvoiceResDto;
import net.vinpos.api.enums.OrderStatus;
import net.vinpos.api.enums.PaymentMethod;
import net.vinpos.api.exception.BadRequestException;
import net.vinpos.api.mapping.rest.InvoiceMapper;
import net.vinpos.api.model.Invoice;
import net.vinpos.api.model.Order;
import net.vinpos.api.model.Shift;
import net.vinpos.api.model.User;
import net.vinpos.api.repository.InvoiceRepository;
import net.vinpos.api.repository.ShiftRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceService extends BaseService<Invoice, InvoiceRepository> {

  private final InvoiceMapper invoiceMapper;
  private final OrderService orderService;
  private final UserService userService;
  private final TableService tableService;
  @PersistenceContext private final EntityManager entityManager;
  private final ShiftRepository shiftRepository;

  public InvoiceService(
          InvoiceRepository repository,
          InvoiceMapper invoiceMapper,
          OrderService orderService,
          UserService userService, TableService tableService,
          EntityManager entityManager, ShiftRepository shiftRepository) {
    super(repository);
    this.invoiceMapper = invoiceMapper;
    this.orderService = orderService;
    this.userService = userService;
    this.tableService = tableService;
    this.entityManager = entityManager;
    this.shiftRepository = shiftRepository;
  }

  @Transactional
  public InvoiceResDto createInvoice(InvoiceReqDto dto) {
    Order order = orderService.getById(dto.getOrderId(), false);

    if (!order.getStatus().equals(OrderStatus.READY_TO_DELIVER)
        && !order.getStatus().equals(OrderStatus.DELIVERED)) {
      throw new BadRequestException("Order is not in a valid state for invoicing");
    }

    String id = getIdFromJwt();
    Instant now = Instant.now();

    Optional<Shift> shiftOptional = shiftRepository.findOpenShiftByManager(id, now);
    Shift shift = shiftOptional.orElseThrow(() -> new BadRequestException("Shift not found"));

    BigDecimal totalAmount =
        order.getOrderItems().stream()
            .map(
                item ->
                    BigDecimal.valueOf(item.getDishPrice())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal changeAmount = BigDecimal.ZERO;

    if (dto.getPaymentMethod() == PaymentMethod.CASH) {
      if (dto.getAmountGiven().compareTo(totalAmount) < 0) {
        throw new RuntimeException("Insufficient amount given");
      }
      changeAmount = dto.getAmountGiven().subtract(totalAmount);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User cashier =
        (authentication instanceof JwtAuthenticationToken token)
            ? userService.getByToken(token, false)
            : new User();

    Invoice invoice =
        Invoice.builder()
            .order(order)
            .invoiceCode(generateInvoiceCode(order.getId()))
            .total(totalAmount)
            .amountGiven(dto.getAmountGiven())
            .changeAmount(changeAmount)
            .paymentMethod(dto.getPaymentMethod())
            .cashier(cashier)
            .shift(shift)
            .build();

    orderService.updateStatusById(order.getId(), OrderStatus.PAID);

    invoice = entityManager.merge(invoice);
    return invoiceMapper.model2Dto(invoice);
  }

  @Transactional
  public InvoiceResDto updateInvoice(UUID id, InvoiceReqDto dto) {
    Invoice invoice =
        repository.findById(id).orElseThrow(() -> new BadRequestException("Invoice not found"));
    invoiceMapper.updateModelFromDto(dto, invoice);
    invoice = entityManager.merge(invoice);
    return invoiceMapper.model2Dto(invoice);
  }

  public InvoiceResDto getInvoiceById(UUID id) {
    Invoice invoice =
        repository.findById(id).orElseThrow(() -> new BadRequestException("Invoice not found"));
    return invoiceMapper.model2Dto(invoice);
  }

  private String generateInvoiceCode(UUID invoiceId) {
    String prefix = "INV";
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    SecureRandom random = new SecureRandom();
    int randomInt = random.nextInt(999999);
    return String.format(
        "%s-%s-%s-%06d", prefix, timestamp, invoiceId.toString().substring(0, 8), randomInt);
  }

  private String getIdFromJwt() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return jwt.getClaim("sub");
  }
}
