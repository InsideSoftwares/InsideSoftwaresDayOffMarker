package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.InitRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.initialization.InitResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.initialization.InitializationDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eConfigurationkey;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ApplicationAlreadyInitializedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.ConfigurationNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.InitializationService;
import br.com.sawcunha.dayoffmarker.specification.service.RequestCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitializationServiceBean implements InitializationService {

    private final ConfigurationServiceBean configurationServiceBean;
    private final RequestCreationService requestCreationService;
    private final CountryService countryService;

    @Transactional(rollbackFor={ConfigurationNotExistException.class, CountryNameInvalidException.class, Exception.class})
    @Override
    public DayOffMarkerResponse<InitResponseDTO> initializationApplication(final InitRequestDTO initRequestDTO) throws Exception {
        boolean initConfig = configurationServiceBean.isInitializedApplication();
        if(initConfig) throw new ApplicationAlreadyInitializedException();

        updateKeyAccess(initRequestDTO);
        updateLimit(initRequestDTO);
        updateCountryDefault(initRequestDTO);

        configurationServiceBean.updateConfiguration(
                eConfigurationkey.INITIAL_CONFIGURATION,
                ConfigurationServiceBean.getInitTrueStatus()
        );

        String country = configurationServiceBean.findValueConfigurationByKey(eConfigurationkey.COUNTRY_DEFAULT);
        String viewKey = configurationServiceBean.findValueConfigurationByKey(eConfigurationkey.ACCESS_KEY_TOKEN_VIEW);
        String adminKey = configurationServiceBean.findValueConfigurationByKey(eConfigurationkey.ACCESS_KEY_TOKEN_ADMIN);

        String keyRequest = requestCreationService.createInitialApplication(country);

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

    @Transactional(readOnly = true)
    @Override
    public DayOffMarkerResponse<InitializationDTO> isInitApplication() throws Exception {
        boolean initConfig = configurationServiceBean.isInitializedApplication();
        return DayOffMarkerResponse.<InitializationDTO>builder()
                .data(
                        InitializationDTO.builder()
                                .init(initConfig)
                                .build()
                )
                .build();
    }

    private void updateKeyAccess(final InitRequestDTO initRequestDTO) throws Exception {
        String adminKey = getKeyUUID(initRequestDTO.getAdminKey());
        String viewKey = getKeyUUID(initRequestDTO.getViewKey());

        configurationServiceBean.updateConfiguration(eConfigurationkey.ACCESS_KEY_TOKEN_ADMIN, adminKey);
        configurationServiceBean.updateConfiguration(eConfigurationkey.ACCESS_KEY_TOKEN_VIEW, viewKey);

    }

    private void updateLimit(final InitRequestDTO initRequestDTO) throws Exception {
        String limitBackYears = getLimitDaysYears(initRequestDTO.getNumberBackYears());
        String limitForwardYears = getLimitDaysYears(initRequestDTO.getNumberForwardYears());
        configurationServiceBean.updateConfiguration(
                eConfigurationkey.LIMIT_BACK_DAYS_YEARS, limitBackYears
        );
        configurationServiceBean.updateConfiguration(
                eConfigurationkey.LIMIT_FORWARD_DAYS_YEARS, limitForwardYears
        );
    }

    private void updateCountryDefault(final InitRequestDTO initRequestDTO) throws Exception {
        if(Objects.nonNull(initRequestDTO.getCountryDefault())){

            boolean isValid = countryService.validCountry(initRequestDTO.getCountryDefault());

            if(!isValid) throw new CountryNameInvalidException();

            configurationServiceBean.updateConfiguration(
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
