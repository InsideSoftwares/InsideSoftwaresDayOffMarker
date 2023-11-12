package br.com.insidesoftwares.dayoffmarker.validator.day;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification.findDayByDayIdAndTagIds;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DayValidatorBean implements ValidatorLink<LinkTagRequestDTO> {

    private final DayRepository dayRepository;
    private final TagRepository tagRepository;

    @Override
    public void validateLink(UUID dayID, LinkTagRequestDTO linkTagRequestDTO) {
        if (!dayRepository.existsById(dayID)) {
            throw new DayNotExistException();
        }
        Long sizeTags = (long) linkTagRequestDTO.tagsID().size();
        if (!tagRepository.existsByTags(sizeTags, linkTagRequestDTO.tagsID())) {
            throw new TagNotExistException();
        }

        LocalDate date = dayRepository.findDateByID(dayID);
        Specification<Day> daySpecification = findDayByDayIdAndTagIds(dayID, linkTagRequestDTO.tagsID());
        if (dayRepository.exists(daySpecification)) {
            throw new TagExistDayException(DateUtils.returnDate(date));
        }
    }
}
