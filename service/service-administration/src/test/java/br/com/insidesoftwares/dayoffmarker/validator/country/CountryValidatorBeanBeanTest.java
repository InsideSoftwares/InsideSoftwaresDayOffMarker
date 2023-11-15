package br.com.insidesoftwares.dayoffmarker.validator.country;

import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
 class CountryValidatorBeanBeanTest {

    private static final String COUNTRY_NAME = "state";
    private static final String COUNTRY_ACRONYM = "acronym";
    private static final String COUNTRY_CODE = "code";
    private static final UUID COUNTRY_ID = UUID.randomUUID();
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private CountryValidatorBean countryValidatorBean;

    @Test
     void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(countryRepository.existsByName(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(countryRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(countryRepository.existsByAcronym(ArgumentMatchers.anyString())).thenReturn(false);

        countryValidatorBean.validator(createCountryRequestDTO());

        Mockito.verify(countryRepository, Mockito.times(1)).existsByName(COUNTRY_NAME);
        Mockito.verify(countryRepository, Mockito.times(1)).existsByCode(COUNTRY_CODE);
        Mockito.verify(countryRepository, Mockito.times(1)).existsByAcronym(COUNTRY_ACRONYM);
    }

    @Test
     void shouldThrowExceptionCountryNameExistExpetionByRunningMethodValidatorDTOParameter() {
        Mockito.when(countryRepository.existsByName(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(
                CountryNameExistExpetion.class,
                () -> countryValidatorBean.validator(createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsByName(COUNTRY_NAME);
        Mockito.verify(countryRepository, Mockito.times(0)).existsByCode(COUNTRY_CODE);
        Mockito.verify(countryRepository, Mockito.times(0)).existsByAcronym(COUNTRY_ACRONYM);
    }

    @Test
     void shouldThrowExceptionCountryCodeExistExpetionByRunningMethodValidatorDTOParameter() {
        Mockito.when(countryRepository.existsByName(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(countryRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(
                CountryCodeExistExpetion.class,
                () -> countryValidatorBean.validator(createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsByName(COUNTRY_NAME);
        Mockito.verify(countryRepository, Mockito.times(1)).existsByCode(COUNTRY_CODE);
        Mockito.verify(countryRepository, Mockito.times(0)).existsByAcronym(COUNTRY_ACRONYM);
    }

    @Test
     void shouldThrowExceptionCountryAcronymExistExpetionByRunningMethodValidatorDTOParameter() {
        Mockito.when(countryRepository.existsByName(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(countryRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(countryRepository.existsByAcronym(ArgumentMatchers.anyString())).thenReturn(true);

        assertThrows(
                CountryAcronymExistExpetion.class,
                () -> countryValidatorBean.validator(createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsByName(COUNTRY_NAME);
        Mockito.verify(countryRepository, Mockito.times(1)).existsByCode(COUNTRY_CODE);
        Mockito.verify(countryRepository, Mockito.times(1)).existsByAcronym(COUNTRY_ACRONYM);
    }

    @Test
     void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(true);
        Mockito.when(
                countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);
        Mockito.when(
                countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);
        Mockito.when(
                countryRepository.existsByAcronymAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);

        countryValidatorBean.validator(COUNTRY_ID, createCountryRequestDTO());

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByNameAndNotId(COUNTRY_NAME, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByCodeAndNotId(COUNTRY_CODE, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByAcronymAndNotId(COUNTRY_ACRONYM, COUNTRY_ID);
    }

    @Test
     void shouldThrowExceptionCountryNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(false);

        assertThrows(
                CountryNotExistException.class,
                () -> countryValidatorBean.validator(COUNTRY_ID, createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByNameAndNotId(COUNTRY_NAME, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByCodeAndNotId(COUNTRY_CODE, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByAcronymAndNotId(COUNTRY_ACRONYM, COUNTRY_ID);
    }

    @Test
     void shouldThrowExceptionCountryNameExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(true);
        Mockito.when(
                countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(true);

        assertThrows(
                CountryNameExistExpetion.class,
                () -> countryValidatorBean.validator(COUNTRY_ID, createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByNameAndNotId(COUNTRY_NAME, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByCodeAndNotId(COUNTRY_CODE, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByAcronymAndNotId(COUNTRY_ACRONYM, COUNTRY_ID);
    }

    @Test
     void shouldThrowExceptionCountryCodeExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(true);
        Mockito.when(
                countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);
        Mockito.when(
                countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(true);

        assertThrows(
                CountryCodeExistExpetion.class,
                () -> countryValidatorBean.validator(COUNTRY_ID, createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByNameAndNotId(COUNTRY_NAME, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByCodeAndNotId(COUNTRY_CODE, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(0))
                .existsByAcronymAndNotId(COUNTRY_ACRONYM, COUNTRY_ID);
    }

    @Test
     void shouldThrowExceptionCountryAcronymExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(true);
        Mockito.when(
                countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);
        Mockito.when(
                countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(false);
        Mockito.when(
                countryRepository.existsByAcronymAndNotId(ArgumentMatchers.anyString(), isA(UUID.class))
        ).thenReturn(true);

        assertThrows(
                CountryAcronymExistExpetion.class,
                () -> countryValidatorBean.validator(COUNTRY_ID, createCountryRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByNameAndNotId(COUNTRY_NAME, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByCodeAndNotId(COUNTRY_CODE, COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1))
                .existsByAcronymAndNotId(COUNTRY_ACRONYM, COUNTRY_ID);
    }

    @Test
     void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(true);
        countryValidatorBean.validator(COUNTRY_ID);
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
    }

    @Test
     void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(countryRepository.existsById(isA(UUID.class))).thenReturn(false);
        assertThrows(
                CountryNotExistException.class,
                () -> countryValidatorBean.validator(COUNTRY_ID)
        );
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
    }

    private CountryRequestDTO createCountryRequestDTO() {
        return CountryRequestDTO.builder()
                .name(CountryValidatorBeanBeanTest.COUNTRY_NAME)
                .code(COUNTRY_CODE)
                .acronym(CountryValidatorBeanBeanTest.COUNTRY_ACRONYM)
                .build();
    }
}
