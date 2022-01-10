package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.InitRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.InitResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ApplicationAlreadyInitializedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.InitializationService;
import br.com.sawcunha.dayoffmarker.specification.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitializationImplementationService implements InitializationService {

    private final ConfigurationImplementationService configurationImplementationService;
    private final RequestService requestService;
    private final CountryService countryService;

    @Transactional(rollbackFor={ConfigurationNotExistException.class, CountryNameInvalidException.class, Exception.class})
    @Override
    public DayOffMarkerResponse<InitResponseDTO> initializationApplication(final InitRequestDTO initRequestDTO) throws Exception {
        boolean initConfig = configurationImplementationService.isInitializedApplication();
        if(initConfig) throw new ApplicationAlreadyInitializedException();

        updateKeyAccess(initRequestDTO);
        updateLimit(initRequestDTO);
        updateCountryDefault(initRequestDTO);

        configurationImplementationService.updateConfiguration(
                eConfigurationkey.INITIAL_CONFIGURATION,
                ConfigurationImplementationService.getInitTrueStatus()
        );

        String country = configurationImplementationService.findValueConfigurationByKey(eConfigurationkey.COUNTRY_DEFAULT);
        String viewKey = configurationImplementationService.findValueConfigurationByKey(eConfigurationkey.ACCESS_KEY_TOKEN_VIEW);
        String adminKey = configurationImplementationService.findValueConfigurationByKey(eConfigurationkey.ACCESS_KEY_TOKEN_ADMIN);

        String keyRequest = requestService.createInitialApplication(country);

        return DayOffMarkerResponse.<InitResponseDTO>builder()
                .data(
                        InitResponseDTO.builder()
                                .adminKey(adminKey)
                                .viewKey(viewKey)
                                .requestKey(keyRequest)
                                .build()
                )
                .build();
    }

    private void updateKeyAccess(final InitRequestDTO initRequestDTO) throws Exception {
        String adminKey = getKeyUUID(initRequestDTO.getAdminKey());
        String viewKey = getKeyUUID(initRequestDTO.getViewKey());

        configurationImplementationService.updateConfiguration(eConfigurationkey.ACCESS_KEY_TOKEN_ADMIN, adminKey);
        configurationImplementationService.updateConfiguration(eConfigurationkey.ACCESS_KEY_TOKEN_VIEW, viewKey);

    }

    private void updateLimit(final InitRequestDTO initRequestDTO) throws Exception {
        String limitBackYears = getLimitDaysYears(initRequestDTO.getNumberBackYears());
        String limitForwardYears = getLimitDaysYears(initRequestDTO.getNumberForwardYears());
        configurationImplementationService.updateConfiguration(
                eConfigurationkey.LIMIT_BACK_DAYS_YEARS, limitBackYears
        );
        configurationImplementationService.updateConfiguration(
                eConfigurationkey.LIMIT_FORWARD_DAYS_YEARS, limitForwardYears
        );
    }

    private void updateCountryDefault(final InitRequestDTO initRequestDTO) throws Exception {
        if(Objects.nonNull(initRequestDTO.getCountryDefault())){

            boolean isValid = countryService.validCountry(initRequestDTO.getCountryDefault());

            if(!isValid) throw new CountryNameInvalidException();

            configurationImplementationService.updateConfiguration(
                    eConfigurationkey.COUNTRY_DEFAULT,
                    initRequestDTO.getCountryDefault()
            );
        }
    }

    private String getLimitDaysYears(final Integer limit) {
        return Objects.nonNull(limit) ? limit.toString() : null;
    }

    private String getKeyUUID(final UUID key){
        return Objects.isNull(key) ? UUID.randomUUID().toString() : key.toString();
    }
}
