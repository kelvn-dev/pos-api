package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.ShiftReqDto;
import net.vinpos.api.dto.rest.response.ShiftResDto;
import net.vinpos.api.mapping.rest.ShiftMapper;
import net.vinpos.api.model.Shift;
import net.vinpos.api.service.rest.ShiftService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/shifts")
@RequiredArgsConstructor
public class ShiftController implements SecuredRestController {
     private final ShiftService shiftService;
     private final ShiftMapper shiftMapper;

     @PostMapping("/start")
     @PreAuthorize("hasAuthority('write:shifts')")
     public ResponseEntity<ShiftResDto> startShift() {
         return ResponseEntity.ok(shiftService.startShift());
     }

    @PutMapping("/{id}/end")
    @PreAuthorize("hasAuthority('write:shifts')")
    public ResponseEntity<ShiftResDto> endShift(@PathVariable UUID id) {
        return ResponseEntity.ok(shiftService.endShift(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftResDto> getById(@PathVariable UUID id) {
        Shift shift = shiftService.getById(id, false);
        return ResponseEntity.ok(shiftMapper.toResDto(shift));
    }

    @GetMapping
    public ResponseEntity<?> getList(
            @PageableDefault(
                    sort = {"createdAt"},
                    direction = Sort.Direction.DESC)
            @ParameterObject
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "") List<String> filter) {
        return ResponseEntity.ok(shiftMapper.model2Dto(shiftService.getList(filter, pageable)));
    }
}
