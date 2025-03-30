package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AssociateFacultyCreateDto extends MemberCreateDto {

    @Schema(example = "1")
    @Positive(message = "idFaculty must be a positive integer.")
    @NotNull(message = "idFaculty cannot be blank.")
    private Long idFaculty;
}
