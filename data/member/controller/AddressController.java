package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.business.AbstractMemberService;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractMemberController;
import cz.cvut.fit.kvasvojt.sinis.abstract_classes.dto.RecordableDeleteEntityCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.AddressService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.CountryService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.MemberService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Address;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Country;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Member;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.AddressCreateDto;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.AddressResponseDto;
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
public class AddressController extends AbstractMemberController<Address, AddressResponseDto, AddressCreateDto> {


    private final AddressService addressService;
    private final CountryService countryService;

    AddressController(AddressService addressService, ModelMapper modelMapper, MemberService memberService, CountryService countryService) {
        super(modelMapper, memberService);
        this.addressService = addressService;
        this.countryService = countryService;
    }

    @Operation(
            summary = "Get all addresses related to member",
            description = "This endpoint return addresses related to member. Endpoint can filter out deleted addresses."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{idMember}/addresses")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #idMember)")
    public ResponseEntity<Page<AddressResponseDto>> getAllByMemberId(
            @PathVariable Long idMember,
            @RequestParam(required = false, defaultValue = "true") boolean isNotDeleted,
            @RequestParam(required = false) Map<String, String> filterParams,
            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return super.getAllByMemberId(idMember, isNotDeleted, filterParams, pageable);
    }

    @Operation(
            summary = "Create address",
            description = "Create address and add it to member."
    )
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(example = "{\"message\": \"Entity with id = 1 not found.\"}")))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addresses")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, #addressCreateDto)")
    public ResponseEntity<AddressResponseDto> create(@Validated @RequestBody AddressCreateDto addressCreateDto, @AuthenticationPrincipal Member loginMember) {

        Address newAddress = convertToEntity(addressCreateDto, loginMember);

        newAddress = this.addressService.create(newAddress);

        return new ResponseEntity<>(convertToDto(newAddress), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Delete address",
            description = "This endpoint will soft delete address. In another words it will fill attributes related to deletion."
    )
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/addresses/{idAddress}")
    @PreAuthorize("@memberAuthorization.isRegistrarOrOwner(authentication, @addressService.getEntityById(#idAddress).getMember().getIdMember() )")
    public ResponseEntity<AddressResponseDto> deletion(@Validated @RequestBody RecordableDeleteEntityCreateDto recordableDeleteEntityCreateDto,
                                                       @Parameter(description = "The id of the address to delete", example = "{{idAddress}}", required = true) @PathVariable Long idAddress,
                                                       @AuthenticationPrincipal Member loginMember) {
        return super.deletion(recordableDeleteEntityCreateDto, idAddress, loginMember);
    }

    @Override
    protected AbstractMemberService<Address> getService() {
        return addressService;
    }

    @Override
    protected Address convertToEntity(AddressCreateDto addressCreateDto, Member createdBy) {

        Country country = this.countryService.getByCountryShort(addressCreateDto.getCountryShort());

        Address newAddress = super.convertToEntity(addressCreateDto, createdBy);
        newAddress.setCountry(country);

        return newAddress;
    }

    @Override
    protected Class<Address> getEntityClass() {
        return Address.class;
    }

    @Override
    protected Class<AddressResponseDto> getDtoClass() {
        return AddressResponseDto.class;
    }
}
