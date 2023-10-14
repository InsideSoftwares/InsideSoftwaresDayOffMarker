package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.tag.Tag;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void linkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) {
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
    public void unlinkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) {
        Day day = daySearchService.findDayByID(dayID);

        linkTagRequestDTO.tagsID().forEach(tagID -> {
            day.getTags().removeIf(tag -> tag.getId().equals(tagID));
        });

        dayRepository.save(day);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
    })
    @Override
    public void defineDayIsHoliday(final Long dayID) {
        Day day = dayRepository.findDayById(dayID).orElseThrow(DayNotExistException::new);

        day.setHoliday(day.getHolidays().stream().anyMatch(Holiday::isNationalHoliday));

        dayRepository.save(day);
    }
}
