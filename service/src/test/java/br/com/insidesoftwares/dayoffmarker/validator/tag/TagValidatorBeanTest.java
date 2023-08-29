package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagLinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.day.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagValidatorBeanTest {

	@Mock
	private TagRepository tagRepository;
	@Mock
	private DayRepository dayRepository;

	@InjectMocks
	private TagValidatorBean tagValidatorBean;

	private static final String SUCCESS = "SUCCESS";
	private static final String ERROR = "SUCCESS";
	private static final Long TAG_ID = 1L;

	@Test
	void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
		tagValidatorBean.validator(createTagRequestDTO(SUCCESS));

		Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(SUCCESS);
	}

	@Test
	void shouldThrowExceptionByRunningMethodValidatorDTOParameter() {
		Mockito.when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(true);
		assertThrows(
				TagCodeExistException.class,
				() -> tagValidatorBean.validator(createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(ERROR);
	}

	@Test
	void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(false);

		tagValidatorBean.validator(TAG_ID, createTagRequestDTO(SUCCESS));

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(SUCCESS, TAG_ID);
	}

	@Test
	void shouldThrowExceptionTagNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		assertThrows(
				TagNotExistException.class,
				() -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(0)).existsByCodeAndNotId(ERROR, TAG_ID);
	}

	@Test
	void shouldThrowExceptionTagCodeExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		Mockito.when(
				tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())
		).thenReturn(true);

		assertThrows(
				TagCodeExistException.class,
				() -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
		Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(ERROR, TAG_ID);
	}

	@Test
	void shouldntThrowExceptionByRunningMethodValidatorLongParameter()  {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		tagValidatorBean.validator(TAG_ID);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
	}

	@Test
	void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
		assertThrows(
				TagNotExistException.class,
				() -> tagValidatorBean.validator(TAG_ID)
		);

		Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
	}

	@Test
	void shouldntThrowExceptionByRunningMethodValidatorLinkUnlink() {
		TagLinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTO();

		Mockito.when(dayRepository.exists(ArgumentMatchers.any(Specification.class))).thenReturn(true);
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);

		tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO);
	}

	@Test
	void shouldThrowTagLinkDateInvalidExceptionByRunningMethodValidatorLinkUnlink() {
		TagLinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTOInvalidDate();

		assertThrows(
			TagLinkDateInvalidException.class,
			() -> tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO)
		);

	}

	@Test
	void shouldThrowTagLinkOneParameterNotNullExceptionByRunningMethodValidatorLinkUnlink() {
		TagLinkRequestDTO tagLinkRequestDTO = TagLinkRequestDTO.builder().build();

		assertThrows(
			TagLinkOneParameterNotNullException.class,
			() -> tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO)
		);

	}

	@Test
	void shouldThrowTagLinkDateInvalidExceptionByRunningMethodValidatorLinkUnlinkDayAndMonthInvalid() {
		TagLinkRequestDTO tagLinkRequestDTO = TagLinkRequestDTO.builder().tagsID(Set.of(1L)).month(3).day(41).build();

		assertThrows(
			TagLinkDateInvalidException.class,
			() -> tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO)
		);
	}

	@Test
	void shouldTagLinkNotExistExceptionExceptionByRunningMethodValidatorLinkUnlink() {
		TagLinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTO();

		Mockito.when(dayRepository.exists(ArgumentMatchers.any(Specification.class))).thenReturn(true);
		Mockito.when(tagRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

		assertThrows(
			TagLinkNotExistException.class,
			() -> tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO)
		);
	}

	@Test
	void shouldTagLinkParameterNotResultExceptionByRunningMethodValidatorLinkUnlink() {
		TagLinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTO();

		Mockito.when(dayRepository.exists(ArgumentMatchers.any(Specification.class))).thenReturn(false);

		assertThrows(
			TagLinkParameterNotResultException.class,
			() -> tagValidatorBean.validateLinkUnlink(tagLinkRequestDTO)
		);
	}

	private TagRequestDTO createTagRequestDTO(final String code){
		return TagRequestDTO.builder().code(code).build();
	}

	private TagLinkRequestDTO createTagLinkRequestDTO() {
		return TagLinkRequestDTO.builder()
			.tagsID(Set.of(1L))
			.day(5)
			.month(5)
			.year(2023)
			.dayOfWeek(DayOfWeek.FRIDAY)
			.dayOfYear(180)
			.build();
	}

	private TagLinkRequestDTO createTagLinkRequestDTOInvalidDate() {
		return TagLinkRequestDTO.builder()
			.tagsID(Set.of(1L))
			.day(32)
			.month(2)
			.year(2023)
			.dayOfWeek(DayOfWeek.FRIDAY)
			.dayOfYear(180)
			.build();
	}
}
