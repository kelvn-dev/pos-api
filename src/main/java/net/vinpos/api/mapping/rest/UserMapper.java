package net.vinpos.api.mapping.rest;

import com.auth0.json.mgmt.permissions.Permission;
import java.util.List;
import net.vinpos.api.dto.rest.request.ProfileReqDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.dto.rest.response.UserResDto;
import net.vinpos.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User dto2Model(ProfileReqDto dto);

  UserResDto model2Dto(User user, List<Permission> permissions);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<UserResDto> model2Dto(Page<User> users);

  void updateModelFromDto(ProfileReqDto dto, @MappingTarget User user);

  @Mapping(target = "avatar", source = "picture")
  @Mapping(
      target = "createdAt",
      expression = "java( user.getCreatedAt().toInstant().getEpochSecond() )")
  @Mapping(
      target = "updatedAt",
      expression = "java( user.getUpdatedAt().toInstant().getEpochSecond() )")
  User auth02Model(com.auth0.json.mgmt.users.User user);
}
