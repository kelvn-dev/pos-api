package net.vinpos.api.controller.rest;

import com.auth0.json.mgmt.permissions.Permission;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.PasswordReqDto;
import net.vinpos.api.dto.rest.request.ProfileReqDto;
import net.vinpos.api.dto.rest.request.UserReqDto;
import net.vinpos.api.mapping.rest.UserMapper;
import net.vinpos.api.model.User;
import net.vinpos.api.service.provider.Auth0Service;
import net.vinpos.api.service.rest.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Hidden
public class UserController implements SecuredRestController {

  private final UserService userService;
  private final UserMapper userMapper;
  private final Auth0Service auth0Service;

  @PostMapping
  @PreAuthorize("hasAuthority('write:users')")
  public ResponseEntity<?> create(@RequestBody UserReqDto reqDto) {
    userService.create(reqDto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('read:users')")
  public ResponseEntity<?> getById(@PathVariable String id) {
    User user = userService.getById(id, false);
    List<Permission> permissions = auth0Service.getPermissionsByUserId(id);
    return ResponseEntity.ok(userMapper.model2Dto(user, permissions));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('write:users')")
  public ResponseEntity<?> updateById(
      @PathVariable String id, @Valid @RequestBody UserReqDto reqDto) {
    userService.updateById(id, reqDto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('delete:users')")
  public ResponseEntity<?> deleteById(@PathVariable String id) {
    userService.deleteById(id);
    return ResponseEntity.ok(null);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('read:users')")
  public ResponseEntity<?> getList(
      JwtAuthenticationToken jwtToken,
      @PageableDefault(
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          @ParameterObject
          Pageable pageable,
      @RequestParam(required = false, defaultValue = "") List<String> filter) {
    return ResponseEntity.ok(userMapper.model2Dto(userService.getList(filter, pageable)));
  }

  @GetMapping("/permissions")
  public ResponseEntity<?> getPermissions() {
    return ResponseEntity.ok(auth0Service.getPermissions());
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(JwtAuthenticationToken jwtToken) {
    User user = userService.getProfile(jwtToken);
    List<Permission> permissions =
        auth0Service.getPermissionsByUserId(jwtToken.getToken().getSubject());
    return ResponseEntity.ok(userMapper.model2Dto(user, permissions));
  }

  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(
      JwtAuthenticationToken jwtToken, @Valid @RequestBody ProfileReqDto dto) {
    userService.updateByToken(jwtToken, dto);
    return ResponseEntity.ok(null);
  }

  @PutMapping("/password")
  public ResponseEntity<?> updatePassword(
      JwtAuthenticationToken jwtToken, @Valid @RequestBody PasswordReqDto dto) {
    userService.updatePassword(jwtToken, dto);
    return ResponseEntity.ok(null);
  }
}
