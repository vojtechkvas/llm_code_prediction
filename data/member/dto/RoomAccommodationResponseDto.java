package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoomAccommodationResponseDto extends RecordableDeleteEntityResponseDto {

    @Schema(example = "88")
    private Long idRoomAccommodation;
    private MemberResponseDto member;
    private RoomResponseDto room;

}
