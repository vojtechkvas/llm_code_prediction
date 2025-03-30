package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.LanguageService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Language;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.LanguageCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.LanguageResponseDto;
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
public class LanguageController extends AbstractMemberController<Language, LanguageResponseDto, LanguageCreateDto> {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService, MemberService memberService, ModelMapper modelMapper) {
        super(modelMapper, memberService);
        this.languageService = languageService;
    }

    @Operation(
            summary = "Get all languages related to member",
            description = "This endpoint return languages related to member. Endpoint can filter out deleted languages."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/languages")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #idMember)")
    public ResponseEntity<Page<LanguageResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam(required = false) Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create language",
            description = "Create language and link it to member."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/languages")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #languageCreateDto)")
    public ResponseEntity<LanguageResponseDto> create(@Validated @RequestBody LanguageCreateDto languageCreateDto, @AuthenticationPrincipal Member loginMember) {

        Language newLanguage = convertToEntity(languageCreateDto, loginMember);
        newLanguage = this.languageService.create(newLanguage);

        return new ResponseEntity<>(convertToDto(newLanguage), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete language",
            description = "This endpoint will soft delete language. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/languages/{idLanguage}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, @languageService.getEntityById(#idLanguage).getMember().getIdMember() )")
    public ResponseEntity<LanguageResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                        @Parameter(description = "The id of the language to delete", example = "{{idLanguage}}", required = true) @PathVariable Long idLanguage,
                                                        @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idLanguage, loginMember);
    }

    @Override
    protected Class<Language> getEntityClass() {
        return Language.class;
    }

    @Override
    protected Class<LanguageResponseDto> getDtoClass() {
        return LanguageResponseDto.class;
    }

    @Override
    protected AbstractMemberService<Language> getService() {
        return languageService;
    }
}
