package br.com.sawcunha.dayoffmarker.controller.authentication;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.AuthRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.AuthResponseDTO;
import br.com.sawcunha.dayoffmarker.specification.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DayOffMarkerResponse<AuthResponseDTO> login(@RequestBody final AuthRequestDTO authRequestDTO) throws Exception {
        return authenticationService.login(authRequestDTO);
    }

}
