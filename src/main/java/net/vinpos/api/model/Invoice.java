package net.vinpos.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;
import net.vinpos.api.enums.PaymentMethod;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Invoice extends BaseModel {
  @Column(name = "invoice_code", nullable = false)
  private String invoiceCode;

  @OneToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  private BigDecimal total;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method")
  private PaymentMethod paymentMethod;

  @Column(name = "amount_given")
  private BigDecimal amountGiven;

  @Column(name = "change_amount")
  private BigDecimal changeAmount;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User cashier;
}
