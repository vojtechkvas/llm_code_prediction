package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LoginDto {

    @Schema(example = "sys@sin.cvut.cz")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(example = "Aa123456")
    @NotBlank(message = "Password cannot be blank")
    private String password;

}
