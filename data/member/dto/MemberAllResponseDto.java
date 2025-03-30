package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.PreferredLangEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MemberAllResponseDto {

    @Schema(example = "13161")
    private Long idMember;

    @Schema(example = "Naftenko")
    private String firstName;

    @Schema(example = "Petrolijevic")
    private String middleName;

    @Schema(example = "Benzinov")
    private String lastName;

    private CountryDto country;

    @Schema(example = "false")
    private boolean identityCheck;

    private MemberResponseDto identityCheckBy;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(type = "string", example = "2027-02-19T18:36:06")
    private LocalDateTime identityCheckAt;

    @Schema(example = "Coralie_Johnson76@hotmail.com")
    private String activeEmail;

    @Schema(example = "Did not find country")
    private String note;

    @Schema(example = "CZ")
    private PreferredLangEnum preferredLanguage;

    @Schema(example = "Praha")
    private String birthCity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "2023-12-14T12:00:00Z")
    private Date birthDate;

    @Schema(example = "2024-04-19T20:44:27")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(type = "string", example = "2027-02-19T18:36:06")
    private LocalDateTime deletionRequestAt;

    private MemberResponseDto deletionRequestBy;

    @Schema(example = "He requested it")
    private String deletionRequestComment;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(type = "string", example = "2027-02-19T18:36:06")
    private LocalDateTime willBeDeleteAt;

}
