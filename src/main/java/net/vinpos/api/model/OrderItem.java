package net.vinpos.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class OrderItem extends BaseModel {

  @Column(name = "quanity")
  private Integer quanity;

  @Column(name = "dish_name")
  private String dishName;

  @Column(name = "dish_name")
  private Double dishPrice;

  @Column(name = "status")
  private Double status;
}
