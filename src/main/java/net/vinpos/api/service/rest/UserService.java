package net.vinpos.api.service.rest;

import lombok.RequiredArgsConstructor;
import net.vinpos.api.dto.rest.request.PasswordReqDto;
import net.vinpos.api.dto.rest.request.UserReqDto;
import net.vinpos.api.enums.MemberShip;
import net.vinpos.api.exception.BadRequestException;
import net.vinpos.api.exception.ForbiddenException;
import net.vinpos.api.exception.NotFoundException;
import net.vinpos.api.mapping.rest.UserMapper;
import net.vinpos.api.model.User;
import net.vinpos.api.repository.UserRepository;
import net.vinpos.api.service.provider.Auth0Service;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final UserRepository repository;
  private final Auth0Service auth0Service;

  public User getById(String id, boolean noException) {
    User user = repository.findById(id).orElse(null);
    if (Objects.isNull(user) && !noException) {
      throw new NotFoundException(User.class, "id", id);
    }
    return user;
  }

  public User getByToken(JwtAuthenticationToken token, boolean noException) {
    String userId = token.getToken().getSubject();
    return this.getById(userId, noException);
  }

  public User getProfile(JwtAuthenticationToken jwtToken) {
    String userId = jwtToken.getToken().getSubject();
    User user = this.getById(userId, true);
    if (Objects.isNull(user)) {
      com.auth0.json.mgmt.users.User auth0User = auth0Service.getUserById(userId);
      user = userMapper.auth02Model(auth0User);
      user = repository.save(user);
    }
    return user;
  }

  public User updateByToken(JwtAuthenticationToken jwtToken, UserReqDto dto) {
    User user = this.getByToken(jwtToken, false);
    userMapper.updateModelFromDto(dto, user);
    return repository.save(user);
  }

  public void updatePassword(JwtAuthenticationToken jwtToken, PasswordReqDto dto) {
    String userId = jwtToken.getToken().getSubject();
    if (!userId.startsWith("auth0")) {
      throw new BadRequestException("Account is of type social");
    }
    User user = this.getByToken(jwtToken, false);
    try {
      auth0Service.login(user.getEmail(), dto.getOldPassword());
    } catch (Exception exception) {
      throw new ForbiddenException("Access denied");
    }
    auth0Service.updatePassword(userId, dto.getNewPassword());
  }
}
