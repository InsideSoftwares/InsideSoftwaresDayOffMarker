package br.com.insidesoftwares.dayoffmarker.validator.city;

import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
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

@ExtendWith(MockitoExtension.class)
class CityValidatorBeanBeanTest {

    private static final String CITY_NAME = "state";
    private static final String CITY_ACRONYM = "acronym";
    private static final String CITY_CODE = "code";
    private static final UUID CITY_ID = UUID.randomUUID();
    private static final UUID STATE_ID = UUID.randomUUID();
    @Mock
    private StateRepository stateRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityValidatorBean cityValidatorBean;

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateID(
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByNameAndStateID(
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);

        cityValidatorBean.validator(createCityRequestDTO());

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateID(CITY_CODE, CITY_ACRONYM, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateID(CITY_CODE, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByNameAndStateID(CITY_NAME, STATE_ID);
    }

    @Test
    void shouldThrowExceptionStateNotExistExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                StateNotExistException.class,
                () -> cityValidatorBean.validator(createCityRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndAcronymAndStateID(CITY_CODE, CITY_ACRONYM, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndStateID(CITY_CODE, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateID(CITY_NAME, STATE_ID);
    }

    @Test
    void shouldThrowExceptionCityCodeAcronymStateExistExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityCodeAcronymStateExistException.class,
                () -> cityValidatorBean.validator(createCityRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateID(CITY_CODE, CITY_ACRONYM, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndStateID(CITY_CODE, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateID(CITY_NAME, STATE_ID);
    }

    @Test
    void shouldThrowExceptionCityCodeStateExistExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateID(
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityCodeStateExistException.class,
                () -> cityValidatorBean.validator(createCityRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateID(CITY_CODE, CITY_ACRONYM, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateID(CITY_CODE, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateID(CITY_NAME, STATE_ID);
    }

    @Test
    void shouldThrowExceptionCityNameStateExistExceptionByRunningMethodValidatorDTOParameter() {
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateID(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateID(
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByNameAndStateID(
                        ArgumentMatchers.anyString(),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityNameStateExistException.class,
                () -> cityValidatorBean.validator(createCityRequestDTO())
        );

        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateID(CITY_CODE, CITY_ACRONYM, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateID(CITY_CODE, STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByNameAndStateID(CITY_NAME, STATE_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByNameAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);

        cityValidatorBean.validator(CITY_ID, createCityRequestDTO());

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldThrowExceptionCityNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                CityNotExistException.class,
                () -> cityValidatorBean.validator(CITY_ID, createCityRequestDTO())
        );

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(0)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldThrowExceptionStateNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                StateNotExistException.class,
                () -> cityValidatorBean.validator(CITY_ID, createCityRequestDTO())
        );

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldThrowExceptionCityCodeAcronymStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityCodeAcronymStateExistException.class,
                () -> cityValidatorBean.validator(CITY_ID, createCityRequestDTO())
        );

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldThrowExceptionCityCodeStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityCodeStateExistException.class,
                () -> cityValidatorBean.validator(CITY_ID, createCityRequestDTO())
        );

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(0))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldThrowExceptionCityNameStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(stateRepository.existsById(any(UUID.class))).thenReturn(true);
        Mockito.when(
                cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByCodeAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(false);
        Mockito.when(
                cityRepository.existsByNameAndStateIDAndNotId(
                        ArgumentMatchers.anyString(),
                        any(UUID.class),
                        any(UUID.class)
                )
        ).thenReturn(true);

        assertThrows(
                CityNameStateExistException.class,
                () -> cityValidatorBean.validator(CITY_ID, createCityRequestDTO())
        );

        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
        Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE, CITY_ACRONYM, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByCodeAndStateIDAndNotId(CITY_CODE, STATE_ID, CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1))
                .existsByNameAndStateIDAndNotId(CITY_NAME, STATE_ID, CITY_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(true);
        cityValidatorBean.validator(CITY_ID);
        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
    }

    @Test
    void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
        Mockito.when(cityRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                CityNotExistException.class,
                () -> cityValidatorBean.validator(CITY_ID)
        );
        Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
    }

    private CityRequestDTO createCityRequestDTO() {
        return CityRequestDTO.builder()
                .name(CityValidatorBeanBeanTest.CITY_NAME)
                .code(CITY_CODE)
                .acronym(CityValidatorBeanBeanTest.CITY_ACRONYM)
                .stateID(CityValidatorBeanBeanTest.STATE_ID)
                .build();
    }
}
