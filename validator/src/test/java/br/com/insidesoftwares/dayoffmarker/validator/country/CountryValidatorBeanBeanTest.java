package br.com.insidesoftwares.dayoffmarker.validator.country;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CountryValidatorBeanBeanTest.class, CountryValidatorBean.class})
public class CountryValidatorBeanBeanTest {

	@MockBean
	private CountryRepository countryRepository;

	@Autowired
	private CountryValidatorBean countryValidatorBean;

	private static final String COUNTRY_NAME = "state";
	private static final String COUNTRY_ACRONYM = "acronym";
	private static final String COUNTRY_CODE = "code";
	private static final Long COUNTRY_ID = 1L;

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(countryRepository.existsByName(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(countryRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
		Mockito.when(countryRepository.existsByAcronym(ArgumentMatchers.anyString())).thenReturn(false);

		countryValidatorBean.validator(createCountryRequestDTO());

		Mockito.verify(countryRepository, Mockito.times(1)).existsByName(COUNTRY_NAME);
		Mockito.verify(countryRepository, Mockito.times(1)).existsByCode(COUNTRY_CODE);
		Mockito.verify(countryRepository, Mockito.times(1)).existsByAcronym(COUNTRY_ACRONYM);
	}

	@Test
	public void shouldThrowExceptionCountryNameExistExpetionByRunningMethodValidatorDTOParameter() {
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
	public void shouldThrowExceptionCountryCodeExistExpetionByRunningMethodValidatorDTOParameter() {
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
	public void shouldThrowExceptionCountryAcronymExistExpetionByRunningMethodValidatorDTOParameter() {
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
	public void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter()  {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);
		Mockito.when(
				countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);
		Mockito.when(
				countryRepository.existsByAcronymAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
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
	public void shouldThrowExceptionCountryNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

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
	public void shouldThrowExceptionCountryNameExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
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
	public void shouldThrowExceptionCountryCodeExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);
		Mockito.when(
				countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
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
	public void shouldThrowExceptionCountryAcronymExistExpetionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				countryRepository.existsByNameAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);
		Mockito.when(
				countryRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);
		Mockito.when(
				countryRepository.existsByAcronymAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
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
	public void shouldntThrowExceptionByRunningMethodValidatorLongParameter()  {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		countryValidatorBean.validator(COUNTRY_ID);
		Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
	}

	@Test
	public void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(countryRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		assertThrows(
				CountryNotExistException.class,
				() -> countryValidatorBean.validator(COUNTRY_ID)
		);
		Mockito.verify(countryRepository, Mockito.times(1)).existsById(COUNTRY_ID);
	}

	private CountryRequestDTO createCountryRequestDTO(){
		return CountryRequestDTO.builder()
				.name(CountryValidatorBeanBeanTest.COUNTRY_NAME)
				.code(COUNTRY_CODE)
				.acronym(CountryValidatorBeanBeanTest.COUNTRY_ACRONYM)
				.build();
	}
}
