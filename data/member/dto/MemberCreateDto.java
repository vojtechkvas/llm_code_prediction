package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class MemberCreateDto {
    @Schema(example = "Naftenko")
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String firstName;

    @Schema(example = "Petrolijevic")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String middleName;

    @Schema(example = "Benzinov")
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String lastName;

    @Schema(example = "false")
    @NotNull(message = "Identity check cannot be blank")
    private boolean identityCheck;

    @Schema(example = "Aa123456")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String password;

    @Schema(example = "{{$randomEmail}}")
    @NotBlank(message = "Active email cannot be blank")
    @Email(message = "Invalid email")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String activeEmail;

    @Schema(example = "Did not find country")
    @NotBlank(message = "Note cannot be blank")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String note;

    @Schema(example = "CZE")
    @Size(max = 3, message = "CountryShort must be at most 3 characters long")
    @NotBlank(message = "Country cannot be blank.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String countryShort;

    @Schema(example = "Praha")
    @NotBlank(message = "Birth city cannot be blank.")
    @Size(max = 256, message = "'${validatedValue}' cannot exceed {max} characters.")
    private String birthCity;

    @Schema(example = "2023-12-14")
    @NotNull(message = "Birth date cannot be blank.")
    @Past(message = "Birth date must be in the past.")
    private Date birthDate;
}
