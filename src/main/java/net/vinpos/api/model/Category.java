package net.vinpos.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Category extends BaseModel {

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  @JsonManagedReference
  private Set<Dish> dishes;

  @PreRemove
  private void removeAssociations() {
    for (Dish dish : dishes) {
      dish.setCategoryId(null);
    }
  }
}
