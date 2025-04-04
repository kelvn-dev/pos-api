package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import net.vinpos.api.enums.PaymentMethod;

@Data
public class InvoiceReqDto {
  @NotNull(message = "Order id is required") private UUID orderId;

  @NotNull(message = "Payment method is required") private PaymentMethod paymentMethod;

  private BigDecimal amountGiven;
}
