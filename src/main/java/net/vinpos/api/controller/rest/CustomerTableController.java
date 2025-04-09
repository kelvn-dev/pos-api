package net.vinpos.api.controller.rest;

import lombok.RequiredArgsConstructor;
import net.vinpos.api.mapping.rest.TableMapper;
import net.vinpos.api.model.TableEntity;
import net.vinpos.api.service.rest.TableService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customer/tables")
@RequiredArgsConstructor
public class CustomerTableController {

  private final TableService tableService;
  private final TableMapper tableMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    TableEntity table = tableService.getById(id, false);
    return ResponseEntity.ok(tableMapper.model2Dto(table));
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
