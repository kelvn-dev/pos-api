package net.vinpos.api.sqli;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.vinpos.api.model.BaseModel;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Book extends BaseModel {
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "author")
  private String author;
}
