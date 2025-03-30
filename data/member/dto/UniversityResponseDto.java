package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UniversityResponseDto {

    @Schema(example = "66")
    private Long idUniversity;

    @Schema(example = "CVUT {{$randomCity}}")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @Schema(example = "CVUT {{$randomCity}}")
    @NotBlank(message = "nameEn cannot be blank")
    private String nameEn;

}
