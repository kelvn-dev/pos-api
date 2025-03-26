package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.TableReqDto;
import net.vinpos.api.mapping.rest.TableMapper;
import net.vinpos.api.model.TableEntity;
import net.vinpos.api.service.rest.TableService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tables")
@RequiredArgsConstructor
public class TableController implements SecuredRestController {

  private final TableService tableService;
  private final TableMapper tableMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('write:tables')")
  public ResponseEntity<?> create(@Valid @RequestBody TableReqDto reqDto) {
    tableService.create(reqDto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    TableEntity table = tableService.getById(id, false);
    return ResponseEntity.ok(tableMapper.model2Dto(table));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('write:dishes')")
  public ResponseEntity<?> updateById(@Valid @RequestBody TableReqDto dto, @PathVariable UUID id) {
    tableService.updateById(id, dto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('delete:dishes')")
  public ResponseEntity<?> deleteById(@PathVariable UUID id) {
    tableService.deleteById(id);
    return ResponseEntity.ok(null);
  }

  @GetMapping
  public ResponseEntity<?> getList(
      @PageableDefault(
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          @ParameterObject
          Pageable pageable,
      @RequestParam(required = false, defaultValue = "") List<String> filter) {
    return ResponseEntity.ok(tableMapper.model2Dto(tableService.getList(filter, pageable)));
  }
}
