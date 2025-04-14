package net.vinpos.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "shift")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Shift extends BaseModel{
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User manager;

    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Invoice> invoices;

    @PreRemove
    private void removeAssociations() {
        for (Invoice invoice : invoices) {
            invoice.setShift(null);
        }
    }
}
