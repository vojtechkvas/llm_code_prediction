package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.DormitoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RoomResponseDto {

    @Schema(example = "99")
    private Long idRoom;

    @Schema(example = "DEJVICKA")
    private DormitoryEnum dormitory;

    @Schema(example = "99")
    private String roomNumber;

}
