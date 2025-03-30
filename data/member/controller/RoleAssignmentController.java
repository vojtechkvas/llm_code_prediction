package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoleAssignmentService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoleService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Role;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoleAssignment;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoleAssignmentCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoleAssignmentResponseDto;
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
public class RoleAssignmentController extends AbstractMemberController<RoleAssignment, RoleAssignmentResponseDto, RoleAssignmentCreateDto> {

    private final RoleAssignmentService roleAssignmentService;
    private final RoleService roleService;

    RoleAssignmentController(RoleAssignmentService roleAssignmentService, MemberService memberService, ModelMapper modelMapper, RoleService roleService) {
        super(modelMapper, memberService);
        this.roleAssignmentService = roleAssignmentService;
        this.roleService = roleService;
    }

    @Operation(
            summary = "Get all role assignment related to member",
            description = "This endpoint return role assignments related to member. Endpoint can filter out deleted role assignments."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/role-assignments")
    @PreAuthorize("@memberAuthorization.isRegistrarOfRoleOrOwner(authentication, #idMember)")
    public ResponseEntity<Page<RoleAssignmentResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create role assignment",
            description = "Create role assignment and link it to member."
    )
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(example = "{\n    \"message\": \"Member already owns this role.\"\n}")))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/role-assignments")
    @PreAuthorize("@memberAuthorization.isRegistrarOfRole(authentication, #roleAssignmentCreateDto.getRole())")
    public ResponseEntity<RoleAssignmentResponseDto> create(@RequestBody @Validated RoleAssignmentCreateDto roleAssignmentCreateDto, @AuthenticationPrincipal Member loginMember) {

        RoleAssignment newRoleAssignment = convertToEntity(roleAssignmentCreateDto, loginMember);
        newRoleAssignment = this.roleAssignmentService.create(newRoleAssignment);

        return new ResponseEntity<>(convertToDto(newRoleAssignment), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Delete role assignment",
            description = "This endpoint will soft delete role assignment. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/role-assignments/{idRoleAssignment}")
    @PreAuthorize("@memberAuthorization.isRegistrarOfRoleOrOwner(authentication, @roleAssignmentService.getEntityById(#idRoleAssignment).getMember().getIdMember() )")
    public ResponseEntity<RoleAssignmentResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                              @Parameter(description = "The id of the role assignment to delete", example = "{{idRoleAssignment}}", required = true) @PathVariable Long idRoleAssignment,
                                                              @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idRoleAssignment, loginMember);
    }

    @Override
    protected RoleAssignment convertToEntity(RoleAssignmentCreateDto roleAssignmentCreateDto, Member loginMember) {

        Role role = this.roleService.getByType(roleAssignmentCreateDto.getRole());

        RoleAssignment newRoleAssignment = super.convertToEntity(roleAssignmentCreateDto, loginMember);
        newRoleAssignment.setRole(role);

        return newRoleAssignment;
    }

    @Override
    protected AbstractMemberService<RoleAssignment> getService() {
        return roleAssignmentService;
    }

    @Override
    protected Class<RoleAssignment> getEntityClass() {
        return RoleAssignment.class;
    }

    @Override
    protected Class<RoleAssignmentResponseDto> getDtoClass() {
        return RoleAssignmentResponseDto.class;
    }
}
