package br.com.insidesoftwares.dayoffmarker.service.batch;

import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.logger.LogService;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.specification.batch.BatchHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class BatchHolidayImplementationService implements BatchHolidayService {

	private final HolidayService holidayService;
	private final DayService dayService;
	private final FixedHolidayService fixedHolidayService;
	private final LogService<BatchHolidayImplementationService> logService;

	@PostConstruct
	public void init(){
		logService.init(BatchHolidayImplementationService.class);
		logService.logInfor("Init BatchUpdateHolidayImplementationService");
	}

	@Override
	public void createHoliday(HolidayCreateRequestDTO holidayCreateRequestDTO) {
		try {
			holidayService.saveHoliday(holidayCreateRequestDTO);
		} catch (Exception e) {
			logService.logError("Not create Holiday", e);
		}
	}

	@Override
	public int getMinDateYear() {
		return dayService.getMinDate().getYear();
	}

	@Override
	public int getMaxDateYear() {
		return dayService.getMaxDate().getYear();
	}

	@Override
	public FixedHoliday findFixedHolidayByID(Long fixedHolidayID) throws FixedHolidayNotExistException {
		return fixedHolidayService.findFixedHolidayById(fixedHolidayID);
	}

	@Override
	public Day findDayByDate(LocalDate daySearch) {
		return dayService.findDayByDate(daySearch);
	}
}
