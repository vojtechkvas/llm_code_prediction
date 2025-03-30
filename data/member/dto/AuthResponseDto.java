package cz.cvut.fit.kvasvojt.sinis.modules.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthResponseDto {

    @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzeXNAc3lzLnN5cyIsImlhdCI6MTcxMzU1MDg5NSwiZXhwIjoxNzEzNTUyNjk1fQ.A4iHlPx00Sa1DThh6j-ndEQA1U0e1OOJ01X0OpDbEdLpOlkPjnLzWuryv7V7-ntlGsscMikchzRF8PPqOHzSYA")
    private String accessToken;

    @Schema(example = "Bearer ")
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
