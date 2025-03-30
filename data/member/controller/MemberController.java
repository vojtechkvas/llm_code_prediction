package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.exception.AttributeOfEntityTakenException;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.CountryService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Country;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Tag(name = "Member")
@RestController
@Validated
public class MemberController extends AbstractModelMapperController<Member, MemberResponseDto, MemberCreateDto> {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final CountryService countryService;

    MemberController(MemberService memberService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CountryService countryService) {
        super(modelMapper);
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.countryService = countryService;
    }

    @Operation(
            summary = "Get all members",
            description = "This endpoint return all members. Endpoint can filter out deleted members and members without identity check."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members")
    @PreAuthorize("@memberAuthorization.canSeeAllMembersAndTheirMemberships(authentication)")
    public ResponseEntity<Page<MemberResponseDto>> getAll(
            @RequestParam Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Member> itemPage = memberService.getALLFiltered(filterParams, pageable);
        Page<MemberResponseDto> itemDTOPage = itemPage.map(this::convertToDto);
        return ResponseEntity.ok(itemDTOPage);

    }

    @Operation(
            summary = "Get member by id",
            description = "This endpoint return member selected by his ID."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #idMember)")
    ResponseEntity<MemberAllResponseDto> getById(
            @Parameter(description = "The id of the member to delete", example = "{{idMember}}", required = true) @PathVariable Long idMember
    ) {
        Member member = this.memberService.getEntityById(idMember);

        return new ResponseEntity<>(convertToAllDto(member), HttpStatus.OK);
    }

    @Operation(
            summary = "Create member",
            description = "Create member."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/members")
    @PreAuthorize("@memberAuthorization.createMember(authentication, #newMemberDto)")
    public ResponseEntity<MemberAllResponseDto> create(@Validated @RequestBody MemberCreateDto newMemberDto, @AuthenticationPrincipal Member loginMember) {

        Member newMember = convertToEntity(newMemberDto, loginMember);
        newMember = this.memberService.create(newMember);

        return new ResponseEntity<>(convertToAllDto(newMember), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete member",
            description = "This endpoint will soft delete member. In another words it will fill attributes related to deletion."
    )
    @ApiResponse(responseCode = "400",
            content = @Content(schema = @Schema(example = "{\"deletedComment\": \"Delete comment cannot be blank.\"}")))


    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/members/{idMember}")
    @PreAuthorize("@memberAuthorization.isRegistrarAndNotOwner(authentication, #idMember)")
    public ResponseEntity<MemberAllResponseDto> deletionRequest(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                                @Parameter(description = "The id of the member to delete", example = "{{idMember}}", required = true) @PathVariable Long idMember,
                                                                @AuthenticationPrincipal Member loginMember) {

        Member deleteMember = this.memberService.getEntityById(idMember);
        deleteMember = this.memberService.deleteRequest(loginMember, recordableDeleteEntityCreateDto.getDeletedComment(), deleteMember);

        return new ResponseEntity<>(convertToAllDto(deleteMember), HttpStatus.OK);
    }

    @Operation(
            summary = "Change password",
            description = "This endpoint will change password of user."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(example = "{\"message\":\"Old password is incorrect.\"}")))
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/passwords")
    @PreAuthorize("@memberAuthorization.changePassword(authentication, #changePasswordPatchDto)")
    public ResponseEntity<MemberAllResponseDto> changePassword(@Validated @RequestBody ChangePasswordPatchDto changePasswordPatchDto) {

        Member updateMember = this.memberService.getEntityById(changePasswordPatchDto.getIdMember());

        updateMember = this.memberService.changePassword(changePasswordPatchDto.getOldPassword(), changePasswordPatchDto.getNewPassword(), updateMember);

        return new ResponseEntity<>(convertToAllDto(updateMember), HttpStatus.OK);
    }

    @Operation(
            summary = "Update member",
            description = "This endpoint will update member."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(example = "{\"message\": \"Member already exist with this email address.\"}")))
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/members/{idMember}")
    @PreAuthorize("@memberAuthorization.updateMember(authentication, #updateMemberDto, @memberService.getEntityById(#idMember))")
    public ResponseEntity<MemberAllResponseDto> update(@Validated @RequestBody MemberUpdateDto updateMemberDto,
                                                       @Parameter(description = "The id of the member to delete", example = "{{idMember}}", required = true) @PathVariable Long idMember,
                                                       @AuthenticationPrincipal Member loginMember) {

        Member member = this.memberService.getEntityById(idMember);

        if (memberService.existsByActiveEmail(updateMemberDto.getActiveEmail()) && !member.getActiveEmail().equals(updateMemberDto.getActiveEmail())) {
            throw new AttributeOfEntityTakenException("Email is already taken!");
        }

        Member newMember = convertToEntityUpdate(member, updateMemberDto, loginMember);
        newMember = this.memberService.update(newMember);

        return new ResponseEntity<>(convertToAllDto(newMember), HttpStatus.OK);
    }


    private Member convertToEntity(MemberCreateDto memberCreateDto, Member identityCheckBy) {

        Member member = convertToEntity(memberCreateDto);

        if (memberCreateDto.isIdentityCheck()) {
            member.setIdentityCheckBy(identityCheckBy);
            member.setIdentityCheck(true);
            member.setIdentityCheckAt(LocalDateTime.now());
        } else {
            member.setIdentityCheck(false);
        }

        return member;
    }

    @Override
    public Member convertToEntity(MemberCreateDto memberCreateDto) {
        Country country = this.countryService.getByCountryShort(memberCreateDto.getCountryShort());

        Member member = modelMapper.map(memberCreateDto, Member.class);
        member.setPassword(passwordEncoder.encode(memberCreateDto.getPassword()));
        member.setCountry(country);
        return member;
    }

    protected MemberAllResponseDto convertToAllDto(Member entity) {
        return modelMapper.map(entity, MemberAllResponseDto.class);
    }


    private Member convertToEntityUpdate(Member member, MemberUpdateDto memberUpdateDto, Member identityCheckBy) {
        Member newMember = convertToEntityUpdate(member, memberUpdateDto);

        if (memberUpdateDto.isIdentityCheck()) {
            newMember.setIdentityCheckBy(identityCheckBy);
            newMember.setIdentityCheck(true);
            newMember.setIdentityCheckAt(LocalDateTime.now());
        } else {
            newMember.setIdentityCheck(false);
        }

        return newMember;
    }

    private Member convertToEntityUpdate(Member member, MemberUpdateDto memberUpdateDto) {
        Country country = this.countryService.getByCountryShort(memberUpdateDto.getCountryShort());

        String passwordOld = member.getPassword();

        modelMapper.map(memberUpdateDto, member);

        if (memberUpdateDto.getPassword() == null || memberUpdateDto.getPassword().isBlank()) {
            member.setPassword(passwordOld);
        } else {
            member.setPassword(passwordEncoder.encode(memberUpdateDto.getPassword()));
        }

        member.setCountry(country);
        return member;
    }


    @Override
    protected Class<Member> getEntityClass() {
        return Member.class;
    }

    @Override
    protected Class<MemberResponseDto> getDtoClass() {
        return MemberResponseDto.class;
    }
}
