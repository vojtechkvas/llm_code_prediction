package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoomAccommodationService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoomService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Room;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.RoomAccommodation;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoomAccommodationCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoomAccommodationResponseDto;
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
public class RoomAccommodationController extends AbstractMemberController<RoomAccommodation, RoomAccommodationResponseDto, RoomAccommodationCreateDto> {


    private final RoomAccommodationService roomAccommodationService;
    private final RoomService roomService;


    public RoomAccommodationController(RoomAccommodationService roomAccommodationService, RoomService roomService, MemberService memberService, ModelMapper modelMapper) {
        super(modelMapper, memberService);
        this.roomAccommodationService = roomAccommodationService;
        this.roomService = roomService;
    }

    @Operation(
            summary = "Get all room accommodations related to member",
            description = "This endpoint return room accommodations related to member. Endpoint can filter out deleted room accommodations."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/room-accommodations")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #idMember)")
    public ResponseEntity<Page<RoomAccommodationResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam(required = false) Map<String, String> filterParams,

            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create room accommodation",
            description = "Create room accommodation and link it to member."
    )
    @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(example = "{\n    \"message\": \"Member with id = 55 already has room.\"\n}")))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/room-accommodations")
    @PreAuthorize("@memberAuthorization.isRegistrar(authentication)")
    public ResponseEntity<RoomAccommodationResponseDto> create(@RequestBody RoomAccommodationCreateDto roomAccommodationCreateDto, @AuthenticationPrincipal Member loginMember) {

        RoomAccommodation newRoomAccommodation = convertToEntity(roomAccommodationCreateDto, loginMember);

        newRoomAccommodation = roomAccommodationService.create(newRoomAccommodation);

        return new ResponseEntity<>(convertToDto(newRoomAccommodation), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete room accommodation",
            description = "This endpoint will soft delete room accommodation. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/room-accommodations/{idRoomAccommodation}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, @roomAccommodationService.getEntityById(#idRoomAccommodation).getMember().getIdMember())")
    public ResponseEntity<RoomAccommodationResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                                 @Parameter(description = "The id of the room accommodation to delete", example = "{{idRoomAccommodation}}", required = true) @PathVariable Long idRoomAccommodation,
                                                                 @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idRoomAccommodation, loginMember);
    }

    @Override
    protected RoomAccommodation convertToEntity(RoomAccommodationCreateDto roomAccommodationCreateDto, Member loginMember) {

        Room room = this.roomService.getEntityById(roomAccommodationCreateDto.getIdRoom());

        RoomAccommodation newRoomAccommodation = super.convertToEntity(roomAccommodationCreateDto, loginMember);

        newRoomAccommodation.setRoom(room);

        return newRoomAccommodation;
    }


    @Override
    protected AbstractMemberService<RoomAccommodation> getService() {
        return roomAccommodationService;
    }

    @Override
    protected Class<RoomAccommodation> getEntityClass() {
        return RoomAccommodation.class;
    }

    @Override
    protected Class<RoomAccommodationResponseDto> getDtoClass() {
        return RoomAccommodationResponseDto.class;
    }
}
