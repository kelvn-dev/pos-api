package net.vinpos.api.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.vinpos.api.enums.OrderStatus;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "app_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Order extends BaseModel {

  @Column(name = "session_id", unique = true, updatable = false)
  private String sessionId;

  @Column(name = "table_number")
  private Integer tableNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private OrderStatus status;

  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.MERGE, CascadeType.REMOVE})
  private Set<OrderItem> orderItems;
}
