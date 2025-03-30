package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LanguageCreateDto extends MemberCreateDto {

    @Schema(example = "CZ")
    private PreferredLangEnum preferredLanguage;
}
