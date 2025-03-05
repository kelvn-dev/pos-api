package net.vinpos.api.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "app_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Order extends BaseModel {

  @Column(name = "number")
  private Integer number;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<OrderItem> orderItems;
}
