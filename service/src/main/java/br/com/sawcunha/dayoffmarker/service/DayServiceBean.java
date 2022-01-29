package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DaysNotConfiguredException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.Tag;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.TagRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class DayServiceBean implements DayService {

    private final DayRepository dayRepository;
	private final TagRepository tagRepository;
	private final ValidatorLink<Long,LinkTagRequestDTO> validateLink;
	private final CountryService countryService;
	private static final LocalDate CUT_OFF_DATE = LocalDate.now();

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			DayOffMarkerGenericException.class,
			TagNotExistException.class,
			TagExistDayException.class
	})
	@Override
	public void linkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) throws DayOffMarkerGenericException {
		Day day = findDayByID(dayID);
		Country country = countryService.findCountryByCountryIdOrDefault(day.getCountry().getId());
		validateLink.validateLink(dayID, linkTagRequestDTO, country.getId());

		linkTagRequestDTO.getTagsID().forEach(tagID -> {
			Tag tag = tagRepository.getById(tagID);
			day.addTag(tag);
		});

		dayRepository.save(day);
	}

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			DayOffMarkerGenericException.class
	})
	@Override
	public void unlinkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) throws DayOffMarkerGenericException {
		Day day = findDayByID(dayID);
		linkTagRequestDTO.getTagsID().forEach(tagID -> {
			day.getTags().removeIf(tag -> tag.getId().equals(tagID));
		});

		dayRepository.save(day);
	}

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			DayOffMarkerGenericException.class
	})
	@Override
	public void linkTagDay(
			final LocalDate date,
			final Tag tag,
			final Country country
	) throws DayOffMarkerGenericException {
		Optional<Day> optionalDay = dayRepository.findByDateAndCountry(date, country);
		optionalDay.ifPresentOrElse(day -> {
			day.addTag(tag);
			dayRepository.save(day);
		}, DayNotExistException::new);
	}

    @Override
    public Day findDayByID(final Long dayID) throws DayOffMarkerGenericException {
        Optional<Day> optionalDay = dayRepository.findById(dayID);
        return optionalDay.orElseThrow(DayNotExistException::new);
    }

	@Override
	public Day findDayIDByDateAndCountry(final LocalDate date, final Country country) throws DayOffMarkerGenericException {
		Optional<Day> optionalDay = dayRepository.findByDateAndCountry(date,country);
		return optionalDay.orElseThrow(DayNotExistException::new);
	}

	@Transactional(rollbackFor = {
            DayNotExistException.class,
    })
    @Override
    public void setDayHoliday(final Long dayID, final boolean isHoliday) throws DayOffMarkerGenericException {
        Day day = findDayByID(dayID);
        day.setHoliday(isHoliday);
        dayRepository.save(day);
    }

	@Override
	public LocalDate getMaxDate() throws DayOffMarkerGenericException {
		return dayRepository.findMaxDateByDate(CUT_OFF_DATE).orElseThrow(DaysNotConfiguredException::new);
	}

	@Override
	public LocalDate getMinDate() throws DayOffMarkerGenericException {
		return dayRepository.findMinDateByDate(CUT_OFF_DATE).orElseThrow(DaysNotConfiguredException::new);
	}

	@Override
	public boolean ownsDays() {
		return dayRepository.ownsDays(CUT_OFF_DATE);
	}
}
