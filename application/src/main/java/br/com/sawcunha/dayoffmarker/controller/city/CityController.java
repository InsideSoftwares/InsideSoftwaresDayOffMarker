package br.com.sawcunha.dayoffmarker.controller.city;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderCity;
import br.com.sawcunha.dayoffmarker.specification.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/v1/city")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<List<CityResponseDTO>> findAll(
            @RequestParam(value = "stateID", required = false) Long stateID,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sizePerPage", required = false, defaultValue = "10") int sizePerPage,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "ID") eOrderCity orderCity
    ) throws Exception {
        return cityService.findAll(stateID, page, sizePerPage, direction, orderCity);
    }

    @GetMapping("/v1/city/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<CityResponseDTO> findById(@PathVariable Long id) throws Exception {
        return cityService.findById(id);
    }

    @PostMapping(value = "/v1/city", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<CityResponseDTO> save(@RequestBody CityRequestDTO cityRequestDTO) throws Exception {
        return cityService.save(cityRequestDTO);
    }

    @PutMapping(value = "/v1/city/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<CityResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CityRequestDTO cityRequestDTO
    ) throws Exception {
        return cityService.update(id, cityRequestDTO);
    }
}
