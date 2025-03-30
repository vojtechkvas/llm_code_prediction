package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberUpdateDto {

    @Schema(example = "Naftenko")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Schema(example = "Petrolijevic")
    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @Schema(example = "Benzinov")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Schema(example = "false")
    @NotNull(message = "Identity check cannot be blank")
    private boolean identityCheck;

    @Schema(example = "Aa123456")
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$|^$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long, or it can be empty.")
    private String password;

    @Schema(example = "{{$randomEmail}}")
    @NotBlank(message = "Active email cannot be blank")
    @Email(message = "Invalid email")
    private String activeEmail;

    @Schema(example = "Did not find country")
    @NotBlank(message = "Note cannot be blank")
    private String note;

    @Schema(example = "CZE")
    @Size(max = 3, message = "CountryShort must be at most 3 characters long")
    @NotBlank(message = "Country cannot be blank.")
    private String countryShort;

    @Schema(example = "Praha")
    @NotBlank(message = "Birth city cannot be blank.")
    private String birthCity;
}
