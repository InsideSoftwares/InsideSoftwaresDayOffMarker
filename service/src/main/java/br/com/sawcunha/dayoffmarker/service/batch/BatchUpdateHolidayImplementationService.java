package br.com.sawcunha.dayoffmarker.service.batch;

import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchUpdateHolidayService;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.service.FixedHolidayService;
import br.com.sawcunha.dayoffmarker.specification.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BatchUpdateHolidayImplementationService implements BatchUpdateHolidayService {

	private final HolidayService holidayService;
	private final DayService dayService;
	private final FixedHolidayService fixedHolidayService;
	private final LogService<BatchUpdateHolidayImplementationService> logService;

	@PostConstruct
	public void init(){
		logService.init(BatchUpdateHolidayImplementationService.class);
		logService.logInfor("Init BatchUpdateHolidayImplementationService");
	}

	@Override
	public void updateHoliday(HolidayRequestDTO holidayRequestDTO) {
		try {
			holidayService.saveHoliday(holidayRequestDTO);
		} catch (Exception e) {
			logService.logError("Not create Holiday", e);
		}
	}

	@Override
	public int getMinDateYear() throws Exception {
		return dayService.getMinDate().getYear();
	}

	@Override
	public int getMaxDateYear() throws Exception {
		return dayService.getMaxDate().getYear();
	}

	@Override
	public FixedHoliday findFixedHolidayByID(Long fixedHolidayID) throws FixedHolidayNotExistException {
		return fixedHolidayService.findFixedHolidayById(fixedHolidayID);
	}

	@Override
	public Day findDayIDByDateAndCountry(LocalDate daySearch, Country country) throws Exception {
		return  dayService.findDayIDByDateAndCountry(daySearch, country);
	}
}
