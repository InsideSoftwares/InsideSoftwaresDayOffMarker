package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.entity.city.CityHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.state.StateHoliday;
import br.com.insidesoftwares.dayoffmarker.repository.city.CityRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.CityService;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.working.WorkingCityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class WorkingCityServiceBean implements WorkingCityService {

	private final CityService cityService;
	private final DayService dayService;
	private final CityRepository cityRepository;

	@Override
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCityByDay(
		final Long cityID,
		final LocalDate date
	) {
		boolean isWorkingDay = isWorkingCityByDay(cityID, date);

		return InsideSoftwaresResponseUtils.wrapResponse(
			WorkingCurrentDayResponseDTO.builder()
				.isWorkingDay(isWorkingDay)
				.build()
		);
	}

	@Override
	public InsideSoftwaresResponse<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(final Long cityID) {

		LocalDate currentDay = LocalDate.now();
		boolean isWorkingDay = isWorkingCityByDay(cityID, currentDay);

		return InsideSoftwaresResponseUtils.wrapResponse(
			WorkingCurrentDayResponseDTO.builder()
				.isWorkingDay(isWorkingDay)
				.build()
		);
	}

	private boolean isWorkingCityByDay(final Long cityID, final LocalDate date){
		City city = cityService.findCityFullHolidayById(cityID);
		boolean isWorkingDay;
		Optional<StateHoliday> stateHolidayOptional = city.getState().getStateHolidays().stream()
			.filter(stateHoliday -> {
				Holiday holiday = stateHoliday.getHoliday();
				return date.isEqual(holiday.getDay().getDate());
			})
			.filter(StateHoliday::isStateHoliday).findFirst();

		Optional<Boolean> isWorkingDayOptional = stateHolidayOptional.map(stateHoliday -> {
			Optional<CityHoliday> cityHolidayOptional = city.getCityHolidays().stream()
				.filter(cityHoliday -> stateHoliday.getId().getHolidayId().equals(cityHoliday.getId().getHolidayId()))
				.filter(cityHoliday -> !cityHoliday.isCityHoliday()).findFirst();

			return cityHolidayOptional.isPresent();
		});

		if(isWorkingDayOptional.isPresent()){
			return isWorkingDayOptional.get();
		} else {
			isWorkingDay = city.getCityHolidays().stream()
				.filter(cityHoliday -> {
					Holiday holiday = cityHoliday.getHoliday();
					return date.isEqual(holiday.getDay().getDate());
				})
				.anyMatch(cityHoliday -> !cityHoliday.isCityHoliday());

			if(!isWorkingDay && city.getCityHolidays().isEmpty()) {
				isWorkingDay = dayService.isDayByDateAndIsWeekend(date, false);
			}
		}

		return isWorkingDay;
	}
}
