package br.com.sawcunha.dayoffmarker.controller.state;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderState;
import br.com.sawcunha.dayoffmarker.specification.service.StateService;
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
public class StateController {

    private final StateService stateService;

    @GetMapping("/v1/state")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<List<StateResponseDTO>> findAll(
            @RequestParam(value = "country", required = false) String nameCountry,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "sizePerPage", required = false, defaultValue = "10") int sizePerPage,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "orderBy", required = false, defaultValue = "ID") eOrderState orderState
    ) throws Exception {
        return stateService.findAll(nameCountry, page, sizePerPage, direction, orderState);
    }

    @GetMapping("/v1/state/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<StateResponseDTO> findById(@PathVariable Long id) throws Exception {
        return stateService.findById(id);
    }

    @PostMapping(value = "/v1/state", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<StateResponseDTO> save(@RequestBody StateRequestDTO stateRequestDTO) throws Exception {
        return stateService.save(stateRequestDTO);
    }

    @PutMapping(value = "/v1/state/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<StateResponseDTO> update(
            @PathVariable Long id,
            @RequestBody StateRequestDTO stateRequestDTO
    ) throws Exception {
        return stateService.update(id, stateRequestDTO);
    }

}
