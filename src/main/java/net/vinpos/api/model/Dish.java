package net.vinpos.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Dish extends BaseModel {
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "image")
  private String image;

  @Column(name = "is_shown")
  private Boolean isShown;

  @Column(name = "category_id", columnDefinition = "uuid")
  private UUID categoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", insertable = false, updatable = false)
  @JsonBackReference
  private Category category;
}
