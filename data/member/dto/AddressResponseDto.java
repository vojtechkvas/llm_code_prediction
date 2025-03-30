package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityAndMemberResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressResponseDto extends RecordableDeleteEntityAndMemberResponseDto {

    @Schema(example = "953")
    private Long idAddress;
    private CountryDto country;
    private MemberResponseDto member;

    @Schema(example = "Kolín")
    private String city;

    @Schema(example = "Pražská")
    private String street;

    @Schema(example = "10")
    private int streetNumber;
}
