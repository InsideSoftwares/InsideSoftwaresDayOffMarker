package br.com.insidesoftwares.dayoffmarker.service.working;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.InsideSoftwaresResponseUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHoliday;
import br.com.insidesoftwares.dayoffmarker.specification.search.city.CitySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.WorkingCityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class WorkingCityServiceBean implements WorkingCityService {

    private final CitySearchService citySearchService;
    private final DaySearchService daySearchService;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCityByDay(
            final UUID cityID,
            final LocalDate date
    ) {
        boolean isWorkingDay = isWorkingCityByDay(cityID, date);

        return InsideSoftwaresResponseUtils.wrapResponse(
                WorkingCurrentDayResponseDTO.builder()
                        .isWorkingDay(isWorkingDay)
                        .build()
        );
    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(final UUID cityID) {

        LocalDate currentDay = LocalDate.now();
        boolean isWorkingDay = isWorkingCityByDay(cityID, currentDay);

        return InsideSoftwaresResponseUtils.wrapResponse(
                WorkingCurrentDayResponseDTO.builder()
                        .isWorkingDay(isWorkingDay)
                        .build()
        );
    }

    private boolean isWorkingCityByDay(final UUID cityID, final LocalDate date) {
        City city = citySearchService.findCityFullHolidayById(cityID);
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

        if (isWorkingDayOptional.isPresent()) {
            return isWorkingDayOptional.get();
        } else {
            isWorkingDay = city.getCityHolidays().stream()
                    .filter(cityHoliday -> {
                        Holiday holiday = cityHoliday.getHoliday();
                        return date.isEqual(holiday.getDay().getDate());
                    })
                    .anyMatch(cityHoliday -> !cityHoliday.isCityHoliday());

            if (!isWorkingDay && city.getCityHolidays().isEmpty()) {
                isWorkingDay = daySearchService.isDayByDateAndIsWeekend(date, false);
            }
        }

        return isWorkingDay;
    }
}
