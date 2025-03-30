package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityAndMemberResponseDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.BlockAccessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlockAccessResponseDto extends RecordableDeleteEntityAndMemberResponseDto {

    @Schema(example = "1")
    private Long idBlockAccess;

    @Schema(example = "NETWORK_ACCESS")
    private BlockAccessTypeEnum blockAccessType;

    @Schema(example = "You complain too much.")
    private String messageForBlockMember;

    @Schema(type = "string", example = "2026-08-15T18:36:06")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime blockedEnd;
}
