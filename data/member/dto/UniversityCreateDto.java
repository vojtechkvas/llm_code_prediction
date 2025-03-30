package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UniversityCreateDto {


    @Schema(example = "CVUT {{countryShort3Chars}}")
    @NotBlank(message = "name cannot be blank")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String name;


    @Schema(example = "CVUT {{countryShort3Chars}}")
    @NotBlank(message = "nameEn english cannot be blank")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String nameEn;
}
