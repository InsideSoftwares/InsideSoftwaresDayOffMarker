package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.TagRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagValidatorBeanTest {

    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "SUCCESS";
    private static final UUID TAG_ID = UUID.randomUUID();
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DayRepository dayRepository;
    @InjectMocks
    private TagValidatorBean tagValidatorBean;

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorDTOParameter() {
        when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(false);
        tagValidatorBean.validator(createTagRequestDTO(SUCCESS));

        Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(SUCCESS);
    }

    @Test
    void shouldThrowExceptionByRunningMethodValidatorDTOParameter() {
        when(tagRepository.existsByCode(ArgumentMatchers.anyString())).thenReturn(true);
        assertThrows(
                TagCodeExistException.class,
                () -> tagValidatorBean.validator(createTagRequestDTO(ERROR))
        );

        Mockito.verify(tagRepository, Mockito.times(1)).existsByCode(ERROR);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(tagRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), any(UUID.class))
        ).thenReturn(false);

        tagValidatorBean.validator(TAG_ID, createTagRequestDTO(SUCCESS));

        Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
        Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(SUCCESS, TAG_ID);
    }

    @Test
    void shouldThrowExceptionTagNotExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(tagRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                TagNotExistException.class,
                () -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
        );

        Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
        Mockito.verify(tagRepository, Mockito.times(0)).existsByCodeAndNotId(ERROR, TAG_ID);
    }

    @Test
    void shouldThrowExceptionTagCodeExistExceptionByRunningMethodValidatorLongAndDTOParameter() {
        when(tagRepository.existsById(any(UUID.class))).thenReturn(true);
        when(
                tagRepository.existsByCodeAndNotId(ArgumentMatchers.anyString(), any(UUID.class))
        ).thenReturn(true);

        assertThrows(
                TagCodeExistException.class,
                () -> tagValidatorBean.validator(TAG_ID, createTagRequestDTO(ERROR))
        );

        Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
        Mockito.verify(tagRepository, Mockito.times(1)).existsByCodeAndNotId(ERROR, TAG_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLongParameter() {
        when(tagRepository.existsById(any(UUID.class))).thenReturn(true);
        tagValidatorBean.validator(TAG_ID);

        Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
    }

    @Test
    void shouldThrowExceptionByRunningMethodValidatorLongParameter() {
        when(tagRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(
                TagNotExistException.class,
                () -> tagValidatorBean.validator(TAG_ID)
        );

        Mockito.verify(tagRepository, Mockito.times(1)).existsById(TAG_ID);
    }

    @Test
    void shouldntThrowExceptionByRunningMethodValidatorLinkUnlink() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkRequestDTO();

        when(dayRepository.exists(any(Specification.class))).thenReturn(true);
        when(tagRepository.existsById(any(UUID.class))).thenReturn(true);

        tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO);
    }

    @Test
    void shouldThrowTagLinkDateInvalidExceptionByRunningMethodValidatorLinkUnlink() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkRequestDTOInvalidDate();

        assertThrows(
                TagLinkDateInvalidException.class,
                () -> tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO)
        );

    }

    @Test
    void shouldThrowTagLinkOneParameterNotNullExceptionByRunningMethodValidatorLinkUnlink() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = TagLinkUnlinkRequestDTO.builder().build();

        assertThrows(
                TagLinkOneParameterNotNullException.class,
                () -> tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO)
        );

    }

    @Test
    void shouldThrowTagLinkDateInvalidExceptionByRunningMethodValidatorLinkUnlinkDayAndMonthInvalid() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = TagLinkUnlinkRequestDTO.builder().tagsID(Set.of(UUID.randomUUID())).month(3).day(41).build();

        assertThrows(
                TagLinkDateInvalidException.class,
                () -> tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO)
        );
    }

    @Test
    void shouldTagLinkNotExistExceptionExceptionByRunningMethodValidatorLinkUnlink() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkRequestDTO();

        when(dayRepository.exists(ArgumentMatchers.any(Specification.class))).thenReturn(true);
        when(tagRepository.existsById(any(UUID.class))).thenReturn(false);

        assertThrows(
                TagLinkNotExistException.class,
                () -> tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO)
        );
    }

    @Test
    void shouldTagLinkParameterNotResultExceptionByRunningMethodValidatorLinkUnlink() {
        TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO = createTagLinkRequestDTO();

        when(dayRepository.exists(ArgumentMatchers.any(Specification.class))).thenReturn(false);

        assertThrows(
                TagLinkParameterNotResultException.class,
                () -> tagValidatorBean.validateLinkUnlink(tagLinkUnlinkRequestDTO)
        );
    }

    private TagRequestDTO createTagRequestDTO(final String code) {
        return TagRequestDTO.builder().code(code).build();
    }

    private TagLinkUnlinkRequestDTO createTagLinkRequestDTO() {
        return TagLinkUnlinkRequestDTO.builder()
                .tagsID(Set.of(UUID.randomUUID()))
                .day(5)
                .month(5)
                .year(2023)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .dayOfYear(180)
                .build();
    }

    private TagLinkUnlinkRequestDTO createTagLinkRequestDTOInvalidDate() {
        return TagLinkUnlinkRequestDTO.builder()
                .tagsID(Set.of(UUID.randomUUID()))
                .day(32)
                .month(2)
                .year(2023)
                .dayOfWeek(DayOfWeek.FRIDAY)
                .dayOfYear(180)
                .build();
    }
}
