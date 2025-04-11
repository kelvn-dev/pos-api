package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.InvoiceReqDto;
import net.vinpos.api.dto.rest.response.InvoiceResDto;
import net.vinpos.api.mapping.rest.InvoiceMapper;
import net.vinpos.api.service.rest.InvoiceService;
import net.vinpos.api.service.rest.OrderService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController implements SecuredRestController {
  private final InvoiceService invoiceService;
  private final OrderService orderService;
  private final InvoiceMapper invoiceMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('write:invoices')")
  public ResponseEntity<?> createInvoice(@RequestBody @Valid InvoiceReqDto invoiceReqDto) {
    InvoiceResDto response = invoiceService.createInvoice(invoiceReqDto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getInvoiceById(@PathVariable UUID id) {
    InvoiceResDto response = invoiceService.getInvoiceById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<?> getList(
      @PageableDefault(
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          @ParameterObject
          Pageable pageable,
      @RequestParam(required = false, defaultValue = "") List<String> filter) {
    return ResponseEntity.ok(invoiceMapper.model2Dto(invoiceService.getList(filter, pageable)));
  }
}
