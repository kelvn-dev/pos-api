package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.FloorReqDto;
import net.vinpos.api.mapping.rest.FloorMapper;
import net.vinpos.api.model.Floor;
import net.vinpos.api.service.rest.FloorService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/floors")
@RequiredArgsConstructor
public class FloorController implements SecuredRestController {

  private final FloorService floorService;
  private final FloorMapper floorMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('write:floors')")
  public ResponseEntity<?> create(@Valid @RequestBody FloorReqDto reqDto) {
    floorService.create(reqDto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Floor floor = floorService.getById(id, false);
    return ResponseEntity.ok(floorMapper.model2Dto(floor));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('write:floors')")
  public ResponseEntity<?> updateById(@Valid @RequestBody FloorReqDto dto, @PathVariable UUID id) {
    floorService.updateById(id, dto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('delete:floors')")
  public ResponseEntity<?> deleteById(@PathVariable UUID id) {
    floorService.deleteById(id);
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
    return ResponseEntity.ok(floorMapper.model2Dto(floorService.getList(filter, pageable)));
  }
}
