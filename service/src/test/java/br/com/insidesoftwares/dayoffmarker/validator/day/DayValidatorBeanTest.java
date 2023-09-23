package br.com.insidesoftwares.dayoffmarker.validator.day;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DayValidatorBeanTest {

    private final LocalDate DATE = LocalDate.now();
    private final Long DAY_ID = 1L;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DayRepository dayRepository;
    @InjectMocks
    private DayValidatorBean dayValidatorBean;

    @Test
    void shouldntThrowExceptionByRunningMethodvalidateLink() {
        Mockito.when(dayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(tagRepository.existsByTags(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(dayRepository.findDateByID(ArgumentMatchers.any())).thenReturn(DATE);
        Mockito.when(dayRepository.existsByDateAndTag(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);

        dayValidatorBean.validateLink(DAY_ID, createLinkTagRequestDTO());
    }

    @Test
    void shouldThrowDayNotExistExceptionByRunningMethodvalidateLink() {
        Mockito.when(dayRepository.existsById(ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                DayNotExistException.class,
                () -> dayValidatorBean.validateLink(DAY_ID, createLinkTagRequestDTO())
        );
    }

    @Test
    void shouldThrowTagNotExistExceptionExceptionByRunningMethodvalidateLink() {
        Mockito.when(dayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(tagRepository.existsByTags(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(false);

        assertThrows(
                TagNotExistException.class,
                () -> dayValidatorBean.validateLink(DAY_ID, createLinkTagRequestDTO())
        );
    }

    @Test
    void shouldThrowTagExistDayExceptionByRunningMethodvalidateLink() {
        Mockito.when(dayRepository.existsById(ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(tagRepository.existsByTags(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        Mockito.when(dayRepository.findDateByID(ArgumentMatchers.any())).thenReturn(DATE);
        Mockito.when(dayRepository.existsByDateAndTag(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);

        assertThrows(
                TagExistDayException.class,
                () -> dayValidatorBean.validateLink(DAY_ID, createLinkTagRequestDTO())
        );
    }

    private LinkTagRequestDTO createLinkTagRequestDTO() {
        return LinkTagRequestDTO.builder()
                .tagsID(Set.of(2L))
                .build();
    }
}
