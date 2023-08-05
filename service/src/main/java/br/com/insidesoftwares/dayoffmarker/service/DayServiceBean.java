package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.link.LinkTagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderDay;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DaysNotConfiguredException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagExistDayException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.entity.day.Tag;
import br.com.insidesoftwares.dayoffmarker.mapper.DayMapper;
import br.com.insidesoftwares.dayoffmarker.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.day.TagRepository;
import br.com.insidesoftwares.dayoffmarker.specification.DaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import br.com.insidesoftwares.exception.error.InsideSoftwaresException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class DayServiceBean implements DayService {

    private final DayRepository dayRepository;
	private final TagRepository tagRepository;
	private final ValidatorLink<LinkTagRequestDTO> validateLink;
	private final DayMapper dayMapper;

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			InsideSoftwaresException.class,
			TagNotExistException.class,
			TagExistDayException.class
	})
	@Override
	public void linkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) {
		Day day = findDayByID(dayID);

		validateLink.validateLink(dayID, linkTagRequestDTO);

		linkTagRequestDTO.tagsID().forEach(tagID -> {
			Tag tag = tagRepository.getReferenceById(tagID);
			day.addTag(tag);
		});

		dayRepository.save(day);
	}

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			InsideSoftwaresException.class
	})
	@Override
	public void unlinkTag(Long dayID, LinkTagRequestDTO linkTagRequestDTO) {
		Day day = findDayByID(dayID);
		linkTagRequestDTO.tagsID().forEach(tagID -> {
			day.getTags().removeIf(tag -> tag.getId().equals(tagID));
		});

		dayRepository.save(day);
	}

    @Override
    public Day findDayByID(final Long dayID) {
        Optional<Day> optionalDay = dayRepository.findById(dayID);
        return optionalDay.orElseThrow(DayNotExistException::new);
    }

	@Override
	public Day findDayByDate(final LocalDate date) {
		Optional<Day> optionalDay = dayRepository.findByDate(date);
		return optionalDay.orElseThrow(DayNotExistException::new);
	}

	@Transactional(rollbackFor = {
            DayNotExistException.class,
    })
    @Override
    public void setDayHoliday(final Long dayID, final boolean isHoliday) {
        Day day = findDayByID(dayID);
        day.setHoliday(isHoliday);
        dayRepository.save(day);
    }

	@Override
	public LocalDate getMaxDate() {
		return dayRepository.findMaxDateByDate(DateUtils.getDateCurrent()).orElseThrow(DaysNotConfiguredException::new);
	}

	@Override
	public LocalDate getMinDate() {
		return dayRepository.findMinDateByDate(DateUtils.getDateCurrent()).orElseThrow(DaysNotConfiguredException::new);
	}

	@Override
	public boolean ownsDays() {
		return dayRepository.ownsDays(DateUtils.getDateCurrent());
	}

	@Override
	public InsideSoftwaresResponse<List<DayDTO>> getAllDays(
		final LocalDate startDate,
		final LocalDate endDate,
		final PaginationFilter<eOrderDay> paginationFilter
	) {
		Pageable pageable = PaginationUtils.createPageable(paginationFilter);

		Page<Day> days = dayRepository.findAll(DaySpecification.findAllByStartDateAndEndDate(startDate, endDate), pageable);

		return InsideSoftwaresResponse.<List<DayDTO>>builder()
			.data(dayMapper.toDTOs(days.getContent()))
			.paginatedDTO(
				PaginationUtils.createPaginated(
					days.getTotalPages(),
					days.getTotalElements(),
					days.getContent().size(),
					paginationFilter.getSizePerPage()
				)
			)
			.build();
	}

	@Override
	public InsideSoftwaresResponse<DayDTO> getDayByID(final Long id) {
		Day day = findDayByID(id);
		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDayDTO(day));
	}

	@Override
	public InsideSoftwaresResponse<DayDTO> getDayByDate(final LocalDate date, final Long tagID, final String tagCode) {

		Specification<Day> daySpecification = DaySpecification.findDayByDateOrTag(date, tagID, tagCode);

		Day day = dayRepository.findOne(daySpecification).orElseThrow(DayNotExistException::new);

		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDayDTO(day));
	}

	@Override
	public InsideSoftwaresResponse<List<DayDTO>> getDaysByTag(final Long tagID) {

		List<Day> days = dayRepository.findDaysByTagId(tagID);

		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
	}

	@Override
	public InsideSoftwaresResponse<List<DayDTO>> getDaysByTag(final String tagCode) {
		List<Day> days = dayRepository.findDaysByTagCode(tagCode);

		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
	}

	@Override
	public InsideSoftwaresResponse<List<DayDTO>> getDaysOfCurrentMonth() {
		LocalDate currentDate = DateUtils.getDateCurrent();
		Month month = currentDate.getMonth();

		LocalDate startDate = LocalDate.of(currentDate.getYear(), month, 1);
		LocalDate endDate = LocalDate.of(currentDate.getYear(), month, month.maxLength());

		List<Day> days = dayRepository.findAllByDateSearch(startDate, endDate);
		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
	}

	@Override
	public InsideSoftwaresResponse<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year) {
		Integer yearSearch = year;
		if(Objects.isNull(yearSearch)){
			LocalDate currentDate = DateUtils.getDateCurrent();
			yearSearch = currentDate.getYear();
		}

		LocalDate startDate = LocalDate.of(yearSearch, month, 1);
		LocalDate endDate = LocalDate.of(yearSearch, month, month.maxLength());

		List<Day> days = dayRepository.findAllByDateSearch(startDate, endDate);
		return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
	}

	@Override
	public boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend) {
		return dayRepository.isWorkingDayByDateAndIsWeekend(date, isWeekend);
	}

	@Override
	public boolean isDaysWithoutHolidaysByByDayAndMonthAndFixedHolidayIDOrNotHoliday(
		final Integer day,
		final Integer month,
		final Long fixedHolidayID
	) {
		Integer year = DateUtils.getDateCurrent().getYear();
		return dayRepository.isDaysWithoutHolidaysByByDayAndMonthAndYearAndFixedHolidayIDOrNotHoliday(day, month, year, fixedHolidayID);
	}

	@Override
	public boolean isDayByDateAndIsHolidayAndIsWeekend(final LocalDate date, final boolean isHoliday, final boolean isWeekend) {
		return dayRepository.isWorkingDayByDateAndIsHolidayAndIsWeekend(date, isHoliday, isWeekend);
	}
}
