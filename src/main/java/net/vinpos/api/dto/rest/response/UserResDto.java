package net.vinpos.api.dto.rest.response;

import com.auth0.json.mgmt.permissions.Permission;
import java.util.List;
import lombok.Data;

@Data
public class UserResDto {
  private String id;
  private String nickname;
  private String email;
  private String avatar;
  private String languageCode;
  private List<Permission> permissions;
}
