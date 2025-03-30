package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;


import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.AuthResponseDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.LoginDto;
import cz.cvut.fit.kvasvojt.sinis.security.CustomUserDetailsService;
import cz.cvut.fit.kvasvojt.sinis.security.JWTGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Member")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JWTGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Operation(
            summary = "Login member",
            description = "This endpoint login existing member.",
            operationId = "login"
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Validated @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtGenerator.generateToken(authentication, customUserDetailsService);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

}
