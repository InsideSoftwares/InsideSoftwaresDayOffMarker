package br.com.insidesoftwares.dayoffmarker.validator.state;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StateValidatorBeanBeanTest {

	@Mock
	private StateRepository stateRepository;
	@Mock
	private CountryRepository countryRepository;

	@InjectMocks
	private StateValidatorBean stateValidatorBean;

	private static final String STATE_NAME = "state";
	private static final String STATE_ACRONYM = "acronym";
	private static final Long STATE_ID = 1L;
	private static final Long COUNTRY_ID = 1L;

	@Test
	void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByNameAndCountryIdAndAcronym(
					ArgumentMatchers.anyString(),
					ArgumentMatchers.anyLong(),
					ArgumentMatchers.anyString()
				)
		).thenReturn(false);
		Mockito.when(
				stateRepository.existsByCountryIdAndAcronym(
						ArgumentMatchers.anyLong(),
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
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
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
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByNameAndCountryIdAndAcronym(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
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
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByNameAndCountryIdAndAcronym(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString()
				)
		).thenReturn(false);
		Mockito.when(
				stateRepository.existsByCountryIdAndAcronym(
						ArgumentMatchers.anyLong(),
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
	void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter()  {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				stateRepository.existsByCountryIdAndAcronymAndNotId(
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
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
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

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
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

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
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByNameAndCountryIdAndAcronymAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
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
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				stateRepository.existsByCountryIdAndAcronymAndNotId(
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				stateRepository.existsByCountryIdAndAcronymAndNotId(
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
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
	void shouldntThrowExceptionByRunningMethodValidatorLongParameter()  {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		stateValidatorBean.validator(STATE_ID);
		Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
	}

	@Test
	void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		assertThrows(
				StateNotExistException.class,
				() -> stateValidatorBean.validator(STATE_ID)
		);
		Mockito.verify(stateRepository, Mockito.times(1)).existsById(STATE_ID);
	}

	private StateRequestDTO createStateRequestDTO(){
		return StateRequestDTO.builder()
				.name(StateValidatorBeanBeanTest.STATE_NAME)
				.acronym(StateValidatorBeanBeanTest.STATE_ACRONYM)
				.countryId(StateValidatorBeanBeanTest.COUNTRY_ID)
				.build();
	}
}
