package net.vinpos.api.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.PasswordReqDto;
import net.vinpos.api.dto.rest.request.UserReqDto;
import net.vinpos.api.mapping.rest.UserMapper;
import net.vinpos.api.model.User;
import net.vinpos.api.service.rest.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController implements SecuredRestController {

  private final UserService userService;
  private final UserMapper userMapper;

  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(JwtAuthenticationToken jwtToken) {
    User user = userService.getProfile(jwtToken);
    return ResponseEntity.ok(userMapper.model2Dto(user));
  }

  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(
      JwtAuthenticationToken jwtToken, @Valid @RequestBody UserReqDto dto) {
    User user = userService.updateByToken(jwtToken, dto);
    return ResponseEntity.ok(userMapper.model2Dto(user));
  }

  @PutMapping("/password")
  public ResponseEntity<?> updatePassword(
      JwtAuthenticationToken jwtToken, @Valid @RequestBody PasswordReqDto dto) {
    userService.updatePassword(jwtToken, dto);
    return ResponseEntity.ok(null);
  }
}
