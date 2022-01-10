package br.com.sawcunha.dayoffmarker.configuration.validator;

import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.StateRequestDTO;
import br.com.sawcunha.dayoffmarker.repository.CityRepository;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.repository.StateRepository;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import br.com.sawcunha.dayoffmarker.validator.city.CityValidator;
import br.com.sawcunha.dayoffmarker.validator.country.CountryValidator;
import br.com.sawcunha.dayoffmarker.validator.fixedholiday.FixedHolidayValidator;
import br.com.sawcunha.dayoffmarker.validator.state.StateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final FixedHolidayRepository fixedHolidayRepository;

    @Bean
    public Validator<Long, CountryRequestDTO> createCountryValidator(){
        return new CountryValidator(countryRepository);
    }

    @Bean
    public Validator<Long, StateRequestDTO> createStateValidator(){
        return new StateValidator(stateRepository, countryRepository);
    }

    @Bean
    public Validator<Long, CityRequestDTO> createCityValidator(){
        return new CityValidator(cityRepository, stateRepository);
    }

    @Bean
    public Validator<Long, FixedHolidayRequestDTO> createFixedHolidayValidator(){
        return new FixedHolidayValidator(fixedHolidayRepository, countryRepository);
    }

}
