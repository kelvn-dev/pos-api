package net.vinpos.api.service.rest;

import com.auth0.json.mgmt.permissions.Permission;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.*;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.dto.rest.request.PasswordReqDto;
import net.vinpos.api.dto.rest.request.ProfileReqDto;
import net.vinpos.api.dto.rest.request.UserReqDto;
import net.vinpos.api.exception.BadRequestException;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.exception.ForbiddenException;
import net.vinpos.api.exception.NotFoundException;
import net.vinpos.api.mapping.rest.UserMapper;
import net.vinpos.api.model.User;
import net.vinpos.api.repository.UserRepository;
import net.vinpos.api.service.provider.Auth0Service;
import net.vinpos.api.utils.HelperUtils;
import net.vinpos.api.utils.PredicateUtils;
import net.vinpos.api.utils.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public User getByEmail(String email, boolean noException) {
    User user = repository.findByEmail(email).orElse(null);
    if (Objects.isNull(user) && !noException) {
      throw new NotFoundException(User.class, "email", email);
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

  public User updateByToken(JwtAuthenticationToken jwtToken, ProfileReqDto dto) {
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

  @Transactional
  public void create(UserReqDto reqDto) {
    User user = this.getByEmail(reqDto.getEmail(), true);
    if (Objects.nonNull(user)) {
      throw new ConflictException(User.class, "email", user.getEmail());
    }
    com.auth0.json.mgmt.users.User auth0User =
        auth0Service.createUser(reqDto.getEmail(), reqDto.getPermissions());
    user = userMapper.auth02Model(auth0User);
    repository.save(user);
  }

  public void updateById(String id, UserReqDto reqDto) {
    this.getById(id, false);
    List<Permission> permissions = auth0Service.getPermissionsByUserId(id);
    List<String> currentPermissions = permissions.stream().map(Permission::getName).toList();
    List<String> requestedPermissions = reqDto.getPermissions();
    Set<String> requestedSet = new HashSet<>(requestedPermissions);
    Set<String> currentSet = new HashSet<>(currentPermissions);

    List<String> permissionToAdd =
        requestedSet.stream().filter(permission -> !currentSet.contains(permission)).toList();

    List<String> permissionToRemove =
        currentSet.stream().filter(permission -> !requestedSet.contains(permission)).toList();

    auth0Service.updatePermissionsByUserId(id, permissionToAdd, permissionToRemove);
  }

  public Page<User> getList(List<String> filter, Pageable pageable) {
    List<SearchCriteria> criteria = HelperUtils.formatSearchCriteria(filter);
    BooleanExpression expression = PredicateUtils.getBooleanExpression(criteria, User.class);
    return repository.findAll(expression, pageable);
  }

  @Transactional
  public void deleteById(String id) {
    User user = this.getById(id, false);
    repository.delete(user);
    auth0Service.deleteUser(id);
  }
}
