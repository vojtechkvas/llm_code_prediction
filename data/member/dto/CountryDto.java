package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryDto {

    @Schema(example = "{{countryShort3Chars}}")
    @NotBlank(message = "CountryShort cannot be blank.")
    @Size(max = 3, message = "CountryShort must be at most 3 characters long.")
    private String countryShort;


    @Schema(example = "Czech Republic {{randomChars}}")
    @NotBlank(message = "CountryLong cannot be blank.")
    @Size(max = 35, message = "countryLong must be at most 35 characters long.")
    private String countryLong;
}
