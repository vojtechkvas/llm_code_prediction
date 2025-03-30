package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomAccommodationCreateDto extends MemberCreateDto {


    @Schema(example = "5")
    @Positive(message = "idRoom must be a positive integer.")
    @NotNull(message = "idRoom cannot be blank.")
    private Long idRoom;
}
