package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.MemberCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressCreateDto extends MemberCreateDto {

    @Schema(example = "CZE")
    @Size(max = 3, message = "CountryShort must be at most 3 characters long")
    @NotBlank(message = "Country cannot be blank.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String countryShort;

    @Schema(example = "Kolín")
    @NotBlank(message = "City cannot be blank.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String city;

    @Schema(example = "Pražská")
    @NotBlank(message = "Street cannot be blank.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String street;

    @Schema(example = "10")
    @Positive(message = "Street number must be a positive integer.")
    private int streetNumber;

}
