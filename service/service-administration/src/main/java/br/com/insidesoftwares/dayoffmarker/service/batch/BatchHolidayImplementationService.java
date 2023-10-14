package br.com.insidesoftwares.dayoffmarker.service.batch;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.search.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.FixedHolidaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
class BatchHolidayImplementationService implements BatchHolidayService {

    private final HolidayService holidayService;
    private final DaySearchService daySearchService;
    private final FixedHolidaySearchService fixedHolidaySearchService;


    @Override
    public void createHoliday(HolidayCreateRequestDTO holidayCreateRequestDTO) {
        log.info("Create Holiday - {}", holidayCreateRequestDTO.name());
        try {
            holidayService.saveHoliday(holidayCreateRequestDTO);
        } catch (Exception e) {
            log.error("Not create Holiday", e);
        }
    }

    @Override
    public int getMinDateYear() {
        return daySearchService.getMinDate().getYear();
    }

    @Override
    public int getMaxDateYear() {
        return daySearchService.getMaxDate().getYear();
    }

    @Override
    public FixedHoliday findFixedHolidayByID(Long fixedHolidayID) throws FixedHolidayNotExistException {
        return fixedHolidaySearchService.findFixedHolidayById(fixedHolidayID);
    }

    @Override
    public Day findDayByDate(LocalDate daySearch) {
        return daySearchService.findDayByDate(daySearch);
    }
}
