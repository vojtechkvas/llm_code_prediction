package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FacultyResponseDto {

    @Schema(example = "51")
    private Long idFaculty;

    @Schema(example = "CVUT {{$randomCity}}")
    private String name;

    @Schema(example = "CVUT {{$randomCity}}")
    private String nameEn;

    private UniversityResponseDto university;

}
