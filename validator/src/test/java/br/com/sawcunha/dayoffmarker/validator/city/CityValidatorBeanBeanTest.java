package br.com.sawcunha.dayoffmarker.validator.city;

import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.sawcunha.dayoffmarker.repository.CityRepository;
import br.com.sawcunha.dayoffmarker.repository.StateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CityValidatorBeanBeanTest.class, CityValidatorBean.class})
public class CityValidatorBeanBeanTest {

	@MockBean
	private StateRepository stateRepository;
	@MockBean
	private CityRepository cityRepository;

	@Autowired
	private CityValidatorBean cityValidatorBean;

	private static final String CITY_NAME = "state";
	private static final String CITY_ACRONYM = "acronym";
	private static final String CITY_CODE = "code";
	private static final Long CITY_ID = 1L;
	private static final Long STATE_ID = 1L;

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() throws DayOffMarkerGenericException {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateID(
					ArgumentMatchers.anyString(),
					ArgumentMatchers.anyString(),
					ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByNameAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
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
	public void shouldThrowExceptionStateNotExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		Assert.assertThrows(
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
	public void shouldThrowExceptionCityCodeAcronymStateExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldThrowExceptionCityCodeStateExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldThrowExceptionCityNameStateExistExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByNameAndStateID(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() throws DayOffMarkerGenericException {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByNameAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
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
	public void shouldThrowExceptionCityNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		Assert.assertThrows(
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
	public void shouldThrowExceptionStateNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		Assert.assertThrows(
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
	public void shouldThrowExceptionCityCodeAcronymStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldThrowExceptionCityCodeStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldThrowExceptionCityNameStateExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(stateRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByCodeAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(false);
		Mockito.when(
				cityRepository.existsByNameAndStateIDAndNotId(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyLong(),
						ArgumentMatchers.anyLong()
				)
		).thenReturn(true);

		Assert.assertThrows(
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
	public void shouldntThrowExceptionByRunningMethodValidatorLongParameter() throws DayOffMarkerGenericException {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		cityValidatorBean.validator(CITY_ID);
		Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
	}

	@Test
	public void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(cityRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		Assert.assertThrows(
				CityNotExistException.class,
				() -> cityValidatorBean.validator(CITY_ID)
		);
		Mockito.verify(cityRepository, Mockito.times(1)).existsById(CITY_ID);
	}

	private CityRequestDTO createCityRequestDTO(){
		return CityRequestDTO.builder()
				.name(CityValidatorBeanBeanTest.CITY_NAME)
				.code(CITY_CODE)
				.acronym(CityValidatorBeanBeanTest.CITY_ACRONYM)
				.stateID(CityValidatorBeanBeanTest.STATE_ID)
				.build();
	}
}
