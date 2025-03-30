package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.UniversityService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.University;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.UniversityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.UniversityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name = "Member")
@RestController
public class UniversityController extends AbstractModelMapperController<University, UniversityResponseDto, UniversityCreateDto> {

    private final UniversityService universityService;

    UniversityController(UniversityService universityService, ModelMapper modelMapper) {
        super(modelMapper);
        this.universityService = universityService;
    }

    @Operation(
            summary = "Get all universities",
            description = "Get all universities."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/universities")
    public Collection<UniversityResponseDto> getAll() {
        return convertManyToDTO(universityService.getAll());
    }

    @Operation(
            summary = "Create university",
            description = "Create university"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    @ApiResponse(responseCode = "409", description = "Entity already deleted",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"University already exist with this universityEn attribute.\"}")))

    @PreAuthorize("hasRole('ROLE_NET_ADMIN') or hasRole('ROLE_REGISTRAR')")
    public ResponseEntity<UniversityResponseDto> create(@Validated @RequestBody UniversityCreateDto universityCreateDto) {

        University newUniversity = this.universityService.create(convertToEntity(universityCreateDto));

        return new ResponseEntity<>(convertToDto(newUniversity), HttpStatus.CREATED);
    }

    @Override
    protected Class<University> getEntityClass() {
        return University.class;
    }

    @Override
    protected Class<UniversityResponseDto> getDtoClass() {
        return UniversityResponseDto.class;
    }
}
