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
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Account extends BaseModel {
  @Column(name = "username", nullable = false, updatable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;
}
