package br.com.sawcunha.dayoffmarker.validator.tag;

import br.com.sawcunha.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
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
@ContextConfiguration(classes = {TagValidatorBeanTest.class, TagValidatorBean.class})
public class TagValidatorBeanTest {

	@MockBean
	private TagRepository tagRepository;
	@MockBean
	private DayRepository dayRepository;

	@Autowired
	private TagValidatorBean tagValidatorBean;

	private static final String SUCCESS = "SUCCESS";
	private static final String ERROR = "SUCCESS";
	private static final Long TAG_ID = 1L;

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() throws DayOffMarkerGenericException {
		Mockito.when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
		tagValidatorBean.validator(createTagRequestDTO(SUCCESS));

		Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(SUCCESS);
	}

	@Test
	public void shouldThrowExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(true);
		Assert.assertThrows(
				TagCodeExistException.class,
				() -> tagValidatorBean.validator(createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(ERROR);
	}

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() throws DayOffMarkerGenericException {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);

		tagValidatorBean.validator(TAG_ID, createTagRequestDTO(SUCCESS));

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(SUCCESS, TAG_ID);
	}

	@Test
	public void shouldThrowExceptionTagNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		Assert.assertThrows(
				TagNotExistException.class,
				() -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(0)).existsByCodeAndNotId(ERROR, TAG_ID);
	}

	@Test
	public void shouldThrowExceptionTagCodeExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(true);

		Assert.assertThrows(
				TagCodeExistException.class,
				() -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(ERROR, TAG_ID);
	}

	@Test
	public void shouldntThrowExceptionByRunningMethodValidatorLongParameter() throws DayOffMarkerGenericException {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		tagValidatorBean.validator(TAG_ID);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
	}

	@Test
	public void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		Assert.assertThrows(
				TagNotExistException.class,
				() -> tagValidatorBean.validator(TAG_ID)
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
	}

	private TagRequestDTO createTagRequestDTO(final String code){
		return TagRequestDTO.builder().code(code).build();
	}
}
