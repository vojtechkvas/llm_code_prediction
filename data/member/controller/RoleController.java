package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.RoleService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Role;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.RoleDto;
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
public class RoleController extends AbstractModelMapperController<Role, RoleDto, RoleDto> {

    private final RoleService roleService;

    RoleController(RoleService roleService, ModelMapper modelMapper) {
        super(modelMapper);
        this.roleService = roleService;
    }

    @Operation(
            summary = "Get all roles",
            description = "This endpoint return all roles, which can be possibly link to member."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/roles")
    public Collection<RoleDto> getAll() {
        return convertManyToDTO(roleService.getAll());
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    protected Class<RoleDto> getDtoClass() {
        return RoleDto.class;
    }
}
