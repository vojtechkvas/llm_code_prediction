package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;


import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleAssignmentCreateDto extends MemberCreateDto {

    @Schema(example = "ROLE_NET_ADMIN")
    @NotNull(message = "Role cannot be blank")
    private RoleTypeEnum role;
}
