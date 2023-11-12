package br.com.insidesoftwares.dayoffmarker.service.day;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderDay;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.day.DayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
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
import java.util.UUID;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification.findDayByDateOrTag;
import static br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification.findWorkingDayByDateAndIsHolidayAndIsWeekend;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class DaySearchServiceBean implements DaySearchService {

    private final DayRepository dayRepository;
    private final DayMapper dayMapper;

    @InsideAudit
    @Override
    public Day findDayByID(final UUID dayID) {
        Optional<Day> optionalDay = dayRepository.findById(dayID);

        return optionalDay.orElseThrow(DayNotExistException::new);
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<DayDTO>> getAllDays(
            final LocalDate startDate,
            final LocalDate endDate,
            final InsidePaginationFilterDTO paginationFilter
    ) {
        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderDay.ID);

        Page<Day> days = dayRepository.findAll(DaySpecification.findAllByStartDateAndEndDate(startDate, endDate), pageable);

        return InsideSoftwaresResponseDTO.<List<DayDTO>>builder()
                .data(dayMapper.toDTOs(days.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
                                days.getTotalPages(),
                                days.getTotalElements(),
                                days.getContent().size(),
                                paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<DayDTO> getDayByID(final UUID id) {
        Day day = findDayByID(id);

        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDayDTO(day));
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<DayDTO> getDayByDate(final LocalDate date, final UUID tagID, final String tagCode) {
        Specification<Day> daySpecification = findDayByDateOrTag(date, tagID, tagCode);

        Day day = dayRepository.findOne(daySpecification).orElseThrow(DayNotExistException::new);

        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDayDTO(day));
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final UUID tagID) {
        List<Day> days = dayRepository.findDaysByTagId(tagID);

        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<DayDTO>> getDaysByTag(final String tagCode) {
        List<Day> days = dayRepository.findDaysByTagCode(tagCode);

        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfCurrentMonth() {
        LocalDate currentDate = DateUtils.getDateCurrent();
        Month month = currentDate.getMonth();

        LocalDate startDate = LocalDate.of(currentDate.getYear(), month, 1);
        LocalDate endDate = LocalDate.of(currentDate.getYear(), month, month.maxLength());

        List<Day> days = dayRepository.findAllByDateSearch(startDate, endDate);
        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<DayDTO>> getDaysOfMonth(final Month month, final Integer year) {
        Integer yearSearch = year;
        if (Objects.isNull(yearSearch)) {
            LocalDate currentDate = DateUtils.getDateCurrent();
            yearSearch = currentDate.getYear();
        }

        LocalDate startDate = LocalDate.of(yearSearch, month, 1);
        LocalDate endDate = LocalDate.of(yearSearch, month, month.maxLength());

        List<Day> days = dayRepository.findAllByDateSearch(startDate, endDate);

        return InsideSoftwaresResponseUtils.wrapResponse(dayMapper.toDTOs(days));
    }

    @InsideAudit
    @Override
    public boolean isDayByDateAndIsWeekend(final LocalDate date, final boolean isWeekend) {
        Specification<Day> daySpecification = findWorkingDayByDateAndIsHolidayAndIsWeekend(date, null, isWeekend);

        return dayRepository.exists(daySpecification);
    }
}
