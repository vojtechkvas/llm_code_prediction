package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {

    @Schema(example = "111")
    private Long idMember;

    @Schema(example = "Naftenko")
    private String firstName;

    @Schema(example = "Petrolijevic")
    private String middleName;

    @Schema(example = "Benzinov")
    private String lastName;

    @Schema(example = "true")
    private boolean identityCheck;

    @Schema(example = "{{$randomEmail}}")
    private String activeEmail;

    @Schema(example = "ENG")
    private PreferredLangEnum preferredLanguage;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(type = "string", example = "2027-02-19T18:36:06")
    private LocalDateTime willBeDeleteAt;
}
