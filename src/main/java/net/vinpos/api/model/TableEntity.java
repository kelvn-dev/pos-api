package net.vinpos.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.vinpos.api.enums.TableStatus;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class TableEntity extends BaseModel {
  @Column(name = "number", nullable = false, unique = true)
  private Integer number;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  private TableStatus status;

  @Column(name = "opened_at")
  private Long openedAt;

  @ManyToOne
  @JoinColumn(name = "floor_id", nullable = false, referencedColumnName = "id")
  private Floor floor;
}
