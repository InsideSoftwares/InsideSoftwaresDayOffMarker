package br.com.sawcunha.dayoffmarker.controller.initialization;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.InitRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.initialization.InitResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.initialization.InitializationDTO;
import br.com.sawcunha.dayoffmarker.specification.service.InitializationService;
import com.trendyol.jdempotent.core.annotation.JdempotentRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(
        value = "/api/v1/initialization",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class InitializationController {

    private final InitializationService initializationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<InitResponseDTO> init(
            @JdempotentRequestPayload @RequestBody final InitRequestDTO initRequestDTO
    ) throws Exception {
        return initializationService.initializationApplication(initRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public DayOffMarkerResponse<InitializationDTO> isInit() throws Exception {
        return initializationService.isInitApplication();
    }

}
