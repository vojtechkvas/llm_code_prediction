package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityAndMemberResponseDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LanguageResponseDto extends RecordableDeleteEntityAndMemberResponseDto {

    @Schema(example = "1")
    @Positive(message = "idLanguage must be a positive integer.")
    @NotNull(message = "idLanguage cannot be blank.")
    private Long idLanguage;


    @Schema(example = "CZ")
    private PreferredLangEnum preferredLanguage;
}
