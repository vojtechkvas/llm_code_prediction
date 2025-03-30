package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityAndMemberResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssociateFacultyResponseDto extends RecordableDeleteEntityAndMemberResponseDto {

    @Schema(example = "100")
    private Long idAssociateFaculty;
    private MemberResponseDto member;
    private FacultyResponseDto faculty;
}
