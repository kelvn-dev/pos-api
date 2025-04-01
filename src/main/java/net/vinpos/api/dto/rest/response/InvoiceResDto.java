package net.vinpos.api.dto.rest.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import net.vinpos.api.enums.PaymentMethod;

@Data
public class InvoiceResDto {
  private UUID id;
  private String invoiceCode;

  private BigDecimal total;
  private BigDecimal amountGiven;
  private BigDecimal changeAmount;

  private PaymentMethod paymentMethod;
  private String cashierName;

  private OrderResDto order;

  private String createdAt;
}
