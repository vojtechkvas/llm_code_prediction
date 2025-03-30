package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.BlockAccessService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.BlockAccess;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.BlockAccessCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.BlockAccessResponseDto;
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
public class BlockAccessController extends AbstractMemberController<BlockAccess, BlockAccessResponseDto, BlockAccessCreateDto> {

    private final BlockAccessService blockAccessService;


    BlockAccessController(ModelMapper modelMapper, BlockAccessService blockAccessService, MemberService memberService) {
        super(modelMapper, memberService);
        this.blockAccessService = blockAccessService;

    }

    @Operation(
            summary = "Get all block accesses related to member",
            description = "This endpoint return block accesses related to member. Endpoint can filter out deleted block accesses."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/block-accesses")
    @PreAuthorize("@memberAuthorization.canSeeAllMembersAndTheirMembershipsOrIsOwner(authentication, #idMember)")
    public ResponseEntity<Page<BlockAccessResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam(required = false) Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create block access",
            description = "Create block access and link it to member."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/block-accesses")
    @PreAuthorize("@memberAuthorization.canBlockAccess(#loginMember, #blockAccessCreateDto)")
    public ResponseEntity<BlockAccessResponseDto> create(
            @Validated @RequestBody BlockAccessCreateDto blockAccessCreateDto,
            @AuthenticationPrincipal Member loginMember
    ) {

        BlockAccess newBlockAccess = convertToEntity(blockAccessCreateDto, loginMember);

        newBlockAccess = this.blockAccessService.create(newBlockAccess);

        return new ResponseEntity<>(convertToDto(newBlockAccess), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete block access",
            description = "This endpoint will soft delete block access. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/block-accesses/{idBlockAccess}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, @blockAccessService.getEntityById(#idBlockAccess).getMember().getIdMember() )")
    public ResponseEntity<BlockAccessResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                           @Parameter(description = "The id of the block membership to delete", example = "{{idBlockAccess}}", required = true) @PathVariable Long idBlockAccess,
                                                           @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idBlockAccess, loginMember);
    }

    @Override
    protected AbstractMemberService<BlockAccess> getService() {
        return blockAccessService;
    }

    @Override
    protected Class<BlockAccess> getEntityClass() {
        return BlockAccess.class;
    }

    @Override
    protected Class<BlockAccessResponseDto> getDtoClass() {
        return BlockAccessResponseDto.class;
    }
}
