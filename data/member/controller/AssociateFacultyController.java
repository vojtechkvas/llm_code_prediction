package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.AssociateFacultyService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.FacultyService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.AssociateFaculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Faculty;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.AssociateFacultyCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.AssociateFacultyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Member")
@RestController
public class AssociateFacultyController extends AbstractMemberController<AssociateFaculty, AssociateFacultyResponseDto, AssociateFacultyCreateDto> {

    private final AssociateFacultyService associateFacultyService;
    private final FacultyService facultyService;

    public AssociateFacultyController(AssociateFacultyService associateFacultyService, FacultyService facultyService, MemberService memberService, ModelMapper modelMapper) {
        super(modelMapper, memberService);
        this.associateFacultyService = associateFacultyService;
        this.facultyService = facultyService;
    }

    @Operation(
            summary = "Get all associate faculties related to member",
            description = "This endpoint return associate faculties related to member. Endpoint can filter out deleted associate faculties."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/associate-faculties")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #idMember)")
    public ResponseEntity<Page<AssociateFacultyResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam(required = false) Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create associate faculty",
            description = "Create associate faculty and link it to member."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/associate-faculties")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #associateFacultyCreateDto)")
    public ResponseEntity<AssociateFacultyResponseDto> create(@Validated @RequestBody AssociateFacultyCreateDto associateFacultyCreateDto, @AuthenticationPrincipal Member loginMember) {

        AssociateFaculty newAssociateFaculty = convertToEntity(associateFacultyCreateDto, loginMember);
        newAssociateFaculty = this.associateFacultyService.create(newAssociateFaculty);

        return new ResponseEntity<>(convertToDto(newAssociateFaculty), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete associate faculty",
            description = "This endpoint will soft delete associate faculty. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/associate-faculties/{idAssociateFaculty}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, @associateFacultyService.getEntityById(#idAssociateFaculty).getMember().getIdMember() )")
    public ResponseEntity<AssociateFacultyResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                                @Parameter(description = "The id of the associate faculty to delete", example = "{{idAssociateFaculty}}", required = true) @PathVariable Long idAssociateFaculty,
                                                                @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idAssociateFaculty, loginMember);
    }

    @Override
    protected AssociateFaculty convertToEntity(AssociateFacultyCreateDto associateFacultyCreateDto, Member loginMember) {

        Faculty faculty = this.facultyService.getEntityById(associateFacultyCreateDto.getIdFaculty());

        AssociateFaculty newAssociateFaculty = super.convertToEntity(associateFacultyCreateDto, loginMember);
        newAssociateFaculty.setFaculty(faculty);

        return newAssociateFaculty;
    }

    @Override
    protected Class<AssociateFaculty> getEntityClass() {
        return AssociateFaculty.class;
    }

    @Override
    protected Class<AssociateFacultyResponseDto> getDtoClass() {
        return AssociateFacultyResponseDto.class;
    }

    @Override
    protected AbstractMemberService<AssociateFaculty> getService() {
        return associateFacultyService;
    }
}
