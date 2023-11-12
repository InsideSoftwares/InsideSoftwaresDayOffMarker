package br.com.insidesoftwares.dayoffmarker.service.day;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.day.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class DayServiceBean implements DayService {

    private final DayRepository dayRepository;
    private final TagRepository tagRepository;
    private final DaySearchService daySearchService;
    private final ValidatorLink<LinkTagRequestDTO> validateLink;

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
            InsideSoftwaresException.class,
            TagNotExistException.class,
            TagExistDayException.class
    })
    @Override
    public void linkTag(UUID dayID, LinkTagRequestDTO linkTagRequestDTO) {
        validateLink.validateLink(dayID, linkTagRequestDTO);

        Day day = daySearchService.findDayByID(dayID);

        linkTagRequestDTO.tagsID().forEach(tagID -> {
            Tag tag = tagRepository.getReferenceById(tagID);
            day.addTag(tag);
        });

        dayRepository.save(day);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
            InsideSoftwaresException.class
    })
    @Override
    public void unlinkTag(UUID dayID, LinkTagRequestDTO linkTagRequestDTO) {
        Day day = daySearchService.findDayByID(dayID);

        linkTagRequestDTO.tagsID().forEach(day::removeTag);

        dayRepository.save(day);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
    })
    @Override
    public void defineDayIsHoliday(final UUID dayID) {
        Day day = dayRepository.findDayById(dayID).orElseThrow(DayNotExistException::new);

        day.setHoliday(day.getHolidays().stream().anyMatch(Holiday::isNationalHoliday));

        dayRepository.save(day);
    }
}
