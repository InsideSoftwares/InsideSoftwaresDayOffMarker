package br.com.insidesoftwares.dayoffmarker.validator.state;

import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateValidatorBeanBeanTest {

    private static final String STATE_NAME = "state";
    private static final String STATE_ACRONYM = "acronym";
    private static final UUID STATE_ID = UUID.randomUUID();
    private static final UUID COUNTRY_ID = UUID.randomUUID();
    @Mock
    private StateRepository stateRepository;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private StateValidatorBean stateValidatorBean;

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                stateRepository.existsByNameAndCountryIdAndAcronym(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(false);
        when(
                stateRepository.existsByCountryIdAndAcronym(
                        any(UUID.class),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(false);

        stateValidatorBean.validator(createStateRequestDTO());

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronym(STATE_NAME, COUNTRY_ID, STATE_ACRONYM);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldThrowExceptionCountryNotExistExceptionByRunningMethodValidatorDTOParameter() {
        when(countryRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                CountryNotExistException.class,
                () -> stateValidatorBean.validator(createStateRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByNameAndCountryIdAndAcronym(STATE_NAME, COUNTRY_ID, STATE_ACRONYM);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldThrowExceptionStateNameCountryAcronymExistExceptionByRunningMethodValidatorDTOParameter() {
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                stateRepository.existsByNameAndCountryIdAndAcronym(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(true);
        assertThrows(
                StateNameCountryAcronymExistException.class,
                () -> stateValidatorBean.validator(createStateRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronym(STATE_NAME, COUNTRY_ID, STATE_ACRONYM);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldThrowExceptionStateCountryAcronymExistExceptionByRunningMethodValidatorDTOParameter() {
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                stateRepository.existsByNameAndCountryIdAndAcronym(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(false);
        when(
                stateRepository.existsByCountryIdAndAcronym(
                        any(UUID.class),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(true);
        assertThrows(
                StateCountryAcronymExistException.class,
                () -> stateValidatorBean.validator(createStateRequestDTO())
        );

        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronym(STATE_NAME, COUNTRY_ID, STATE_ACRONYM);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        when(
                stateRepository.existsByCountryIdAndAcronymAndNotId(
                        any(UUID.class),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);

        stateValidatorBean.validator(STATE_ID, createStateRequestDTO());

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronymAndNotId(STATE_NAME, COUNTRY_ID, STATE_ACRONYM, STATE_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByCountryIdAndAcronymAndNotId(COUNTRY_ID, STATE_ACRONYM, STATE_ID);
    }

    @Test
    void shouldThrowExceptionStateNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                StateNotExistException.class,
                () -> stateValidatorBean.validator(STATE_ID, createStateRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(countryRepository, Mockito.times(0)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByNameAndCountryIdAndAcronym(STATE_NAME, COUNTRY_ID, STATE_ACRONYM);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldThrowExceptionCountryNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        when(countryRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                CountryNotExistException.class,
                () -> stateValidatorBean.validator(STATE_ID, createStateRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByNameAndCountryIdAndAcronymAndNotId(STATE_NAME, COUNTRY_ID, STATE_ACRONYM, STATE_ID);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByCountryIdAndAcronymAndNotId(COUNTRY_ID, STATE_ACRONYM, STATE_ID);
    }

    @Test
    void shouldThrowExceptionStateNameCountryAcronymExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                StateNameCountryAcronymExistException.class,
                () -> stateValidatorBean.validator(STATE_ID, createStateRequestDTO())
        );


        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronymAndNotId(STATE_NAME, COUNTRY_ID, STATE_ACRONYM, STATE_ID);
        Mockito.verify(stateRepository, Mockito.times(0))
                .existsByCountryIdAndAcronym(COUNTRY_ID, STATE_ACRONYM);
    }

    @Test
    void shouldThrowExceptionStateCountryAcronymExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        when(countryRepository.existsById(any(UUID.class))).thenReturn(true);

        when(
                stateRepository.existsByCountryIdAndAcronymAndNotId(
                        any(UUID.class),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                StateCountryAcronymExistException.class,
                () -> stateValidatorBean.validator(STATE_ID, createStateRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByNameAndCountryIdAndAcronymAndNotId(STATE_NAME, COUNTRY_ID, STATE_ACRONYM, STATE_ID);
        Mockito.verify(stateRepository, Mockito.times(1))
                .existsByCountryIdAndAcronymAndNotId(COUNTRY_ID, STATE_ACRONYM, STATE_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        stateValidatorBean.validator(STATE_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
    }

    @Test
    void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
        when(stateRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                StateNotExistException.class,
                () -> stateValidatorBean.validator(STATE_ID)
        );
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
    }

    private StateRequestDTO createStateRequestDTO() {
        return StateRequestDTO.builder()
                .name(StateValidatorBeanBeanTest.STATE_NAME)
                .acronym(StateValidatorBeanBeanTest.STATE_ACRONYM)
                .countryId(StateValidatorBeanBeanTest.COUNTRY_ID)
                .build();
    }
}
