package net.vinpos.api.dto.rest.response;

import lombok.Data;
import net.vinpos.api.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class ShiftResDto {
    private UUID id;
    private Instant startTime;
    private Instant endTime;
    private boolean isOpen;
    private String managerName;
    private int totalInvoices;
    private BigDecimal totalRevenue;
    private Set<InvoiceResDto> invoices;
}