package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.FacultyService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.UniversityService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Faculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.University;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.FacultyCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.FacultyResponseDto;
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
public class FacultyController extends AbstractModelMapperController<Faculty, FacultyResponseDto, FacultyCreateDto> {

    private final FacultyService facultyService;
    private final UniversityService universityService;

    FacultyController(FacultyService facultyService, UniversityService universityService, ModelMapper modelMapper) {
        super(modelMapper);
        this.facultyService = facultyService;
        this.universityService = universityService;
    }

    @Operation(
            summary = "Get all faculties",
            description = "Get all faculties."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/faculties")
    public Collection<FacultyResponseDto> getAll() {
        return convertManyToDTO(facultyService.getAll());
    }

    @Operation(
            summary = "Create faculty",
            description = "Create faculty link to university"
    )
    @ApiResponse(responseCode = "409", description = "Entity already deleted",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"FacultyEn already exist on this university.\"}")))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/faculties")
    @PreAuthorize("@memberAuthorization.isRegistrar(authentication)")
    public ResponseEntity<FacultyResponseDto> create(@Validated @RequestBody FacultyCreateDto newFaculty) {

        Faculty newEntityFaculty = this.facultyService.create(convertToEntity(newFaculty));

        return new ResponseEntity<>(convertToDto(newEntityFaculty), HttpStatus.CREATED);
    }

    @Override
    protected Faculty convertToEntity(FacultyCreateDto facultyCreateDto) {

        University university = this.universityService.getEntityById(facultyCreateDto.getIdUniversity());

        Faculty faculty = super.convertToEntity(facultyCreateDto);
        faculty.setUniversity(university);
        return faculty;
    }

    @Override
    protected Class<Faculty> getEntityClass() {
        return Faculty.class;
    }

    @Override
    protected Class<FacultyResponseDto> getDtoClass() {
        return FacultyResponseDto.class;
    }
}
