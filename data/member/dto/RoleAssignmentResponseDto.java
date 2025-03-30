package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;


import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityAndMemberResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleAssignmentResponseDto extends RecordableDeleteEntityAndMemberResponseDto {

    @Schema(example = "222")
    private Long idRoleAssignment;

    private RoleDto role;
}
