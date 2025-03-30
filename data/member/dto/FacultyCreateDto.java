package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FacultyCreateDto {

    @Schema(example = "1")
    @Positive(message = "idUniversity must be a positive integer.")
    @NotNull(message = "idUniversity cannot be blank.")
    private Long idUniversity;

    @Schema(example = "FIT {{randomChars}}")
    @NotBlank(message = "name cannot be blank")
    @Size(max = 60, message = "nameEn must be at most 60 characters long")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String name;

    @Schema(example = "FIT {{randomChars}}")
    @NotBlank(message = "nameEn english cannot be blank")
    @Size(max = 60, message = "nameEn must be at most 60 characters long")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String nameEn;


}
