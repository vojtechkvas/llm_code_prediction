package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoleDto {

    @Schema(example = "ROLE_NET_ADMIN")
    private RoleTypeEnum type;

    @Schema(example = "Take cares of network.")
    private String description;

}
