package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChangePasswordPatchDto {

    @Schema(example = "7227")
    @Positive(message = "idMember must be a positive integer.")
    @NotNull(message = "idMember cannot be blank.")
    private Long idMember;

    @Schema(example = "Aa123456")
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$|^$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long, or it can be empty.")
    private String oldPassword;

    @Schema(example = "Aa1234567")
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.")
    @NotBlank(message = "New password cannot be blank.")
    private String newPassword;
}
