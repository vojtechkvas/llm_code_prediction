package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoomService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Room;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoomResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "Member")
@RestController
public class RoomController extends AbstractModelMapperController<Room, RoomResponseDto, RoomResponseDto> {

    private final RoomService roomService;

    RoomController(RoomService roomService, ModelMapper modelMapper) {
        super(modelMapper);
        this.roomService = roomService;
    }

    @Operation(
            summary = "Get all rooms",
            description = "This endpoint return all rooms, which can be possibly link to member."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/rooms")
    public Collection<RoomResponseDto> getAll() {
        return convertManyToDTO(roomService.getAll());
    }

    @Override
    protected Class<Room> getEntityClass() {
        return Room.class;
    }

    @Override
    protected Class<RoomResponseDto> getDtoClass() {
        return RoomResponseDto.class;
    }
}
