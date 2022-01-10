package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.AuthRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.AuthResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.enums.ePermission;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.InvalidKeyAccessException;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.commons.utils.jwt.JwtTokenUtil;
import br.com.sawcunha.dayoffmarker.repository.ConfigurationRepository;
import br.com.sawcunha.dayoffmarker.specification.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationImplementationService implements AuthenticationService {

    private final ConfigurationRepository configurationRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LogService<AuthenticationImplementationService> logService;

    @PostConstruct
    public void init(){
        logService.init(AuthenticationImplementationService.class);
        logService.logInfor("Init AuthenticationImplementationService");
    }

    @Override
    @Transactional
    public DayOffMarkerResponse<AuthResponseDTO> login(final @Valid AuthRequestDTO authDTO) throws Exception {
        try {
            eConfigurationkey key = configurationRepository.findConfigurationKeyByValue(authDTO.getKey().toString());

            if(Objects.isNull(key)) throw new InvalidKeyAccessException();

            ePermission permission;
            switch (key) {
                case ACCESS_KEY_TOKEN_VIEW -> permission = ePermission.VIEW;
                case ACCESS_KEY_TOKEN_ADMIN -> permission = ePermission.ADMIN;
                default -> throw new InvalidKeyAccessException();
            }

            return DayOffMarkerResponse.<AuthResponseDTO>builder()
                    .data(
                            AuthResponseDTO.builder()
                                    .token(
                                            jwtTokenUtil.generateToken(
                                                    authDTO.getName(),
                                                    authDTO.getMail(),
                                                    permission
                                            )
                                    )
                                    .request(LocalDateTime.now())
                                    .expiretionTime(LocalDateTime.now().plusMinutes(jwtTokenUtil.getValid()))
                                    .expiretion(jwtTokenUtil.getValid())
                                    .build()
                    )
                    .build();

        } catch (InvalidKeyAccessException invalidKeyAccessException){
            logService.logError(
                    invalidKeyAccessException.getCode(),
                    "Key Invalid",
                    invalidKeyAccessException.getCause()
            );
            throw invalidKeyAccessException;
        } catch (Exception e){
            logService.logError(e.getMessage(),e.getCause());
            throw new DayOffMarkerGenericException();
        }
    }
}
