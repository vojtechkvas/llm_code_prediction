package cz.cvut.fit.kvasvojt.sinis.modules.member.controller;

import cz.cvut.fit.kvasvojt.sinis.abstract_classes.controller.AbstractModelMapperController;
import cz.cvut.fit.kvasvojt.sinis.modules.member.business.CountryService;
import cz.cvut.fit.kvasvojt.sinis.modules.member.domain.Country;
import cz.cvut.fit.kvasvojt.sinis.modules.member.dto.CountryDto;
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
public class CountryController extends AbstractModelMapperController<Country, CountryDto, CountryDto> {

    private final CountryService countryService;


    CountryController(CountryService countryService, ModelMapper modelMapper) {
        super(modelMapper);
        this.countryService = countryService;
    }

    @Operation(
            summary = "Get all countries",
            description = "Get all countries for address creation and birth country."
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/countries")
    public Collection<CountryDto> getAll() {
        return convertManyToDTO(countryService.getAllSorted());
    }


    @Operation(
            summary = "Create country",
            description = "Create country for address creation and birth country."
    )
    @ApiResponse(responseCode = "409", description = "Entity already deleted",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"Country already exist with this country_short attribute.\"}")))

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/countries")
    @PreAuthorize("@memberAuthorization.isRegistrar(authentication)")
    public ResponseEntity<CountryDto> create(@Validated @RequestBody CountryDto newCountry) {

        this.countryService.create(convertToEntity(newCountry));

        return new ResponseEntity<>(newCountry, HttpStatus.CREATED);
    }

    @Override
    protected Class<Country> getEntityClass() {
        return Country.class;
    }

    @Override
    protected Class<CountryDto> getDtoClass() {
        return CountryDto.class;
    }

}
