package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.UserReqDto;
import net.vinpos.api.dto.rest.response.UserResDto;
import net.vinpos.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User dto2Model(UserReqDto dto);

  UserResDto model2Dto(User user);

  //  UserCacheDto model2Cache(User user);

  void updateModelFromDto(UserReqDto dto, @MappingTarget User user);

  @Mapping(target = "avatar", source = "picture")
  @Mapping(
      target = "createdAt",
      expression = "java( user.getCreatedAt().toInstant().getEpochSecond() )")
  @Mapping(
      target = "updatedAt",
      expression = "java( user.getUpdatedAt().toInstant().getEpochSecond() )")
  User auth02Model(com.auth0.json.mgmt.users.User user);
}
