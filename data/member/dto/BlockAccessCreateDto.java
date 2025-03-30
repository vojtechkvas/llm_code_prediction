package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.enums.BlockAccessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlockAccessCreateDto extends MemberCreateDto {

    @Schema(example = "NETWORK_ACCESS")
    private BlockAccessTypeEnum blockAccessType;

    @Schema(example = "He complain too much.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String messageForBlockMember;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(type = "string", example = "2026-08-15T18:36:06")
    @NotNull(message = "Blocked end cannot be blank.")
    private LocalDateTime blockedEnd;

    @Schema(hidden = true)
    @AssertTrue(message = "Blocked end must be in the future.")
    public boolean isBlockedEndInFuture() {
        return blockedEnd.isAfter(LocalDateTime.now());
    }

}
