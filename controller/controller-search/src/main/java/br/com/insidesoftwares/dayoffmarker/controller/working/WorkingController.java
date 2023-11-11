package br.com.insidesoftwares.dayoffmarker.controller.working;

import br.com.insidesoftwares.commons.annotation.InsideSoftwaresController;
import br.com.insidesoftwares.commons.annotation.request.InsideRequestGet;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.day.DayDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.working.WorkingCurrentDayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.WorkingCityService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.WorkingStateService;
import br.com.insidesoftwares.dayoffmarker.specification.search.working.day.WorkingDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

@InsideSoftwaresController
@RequiredArgsConstructor
public class WorkingController {

    private final WorkingDayService workingDayService;
    private final WorkingStateService workingStateService;
    private final WorkingCityService workingCityService;

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
    @InsideRequestGet(uri = "/v1/working/day/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<DayDTO> findWorkingDay(
            @PathVariable final LocalDate date,
            @RequestParam(value = "numberOfDays", required = false, defaultValue = "0") final int numberOfDays
    ) {
        return workingDayService.findNextWorkingDay(date, numberOfDays);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
    @InsideRequestGet(uri = "/v1/working/day/previous/{date}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<DayDTO> findPreviousWorkingDay(
            @PathVariable final LocalDate date,
            @RequestParam(value = "numberOfDays", required = false, defaultValue = "0") final int numberOfDays
    ) {
        return workingDayService.findPreviousWorkingDay(date, numberOfDays);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingDay.Read')")
    @InsideRequestGet(uri = "/v1/working/current/day", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDay() {
        return workingDayService.findWorkingCurrentDay();
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
    @InsideRequestGet(uri = "/v1/working/day/{date}/state/{stateID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingStateByDay(
            @PathVariable final LocalDate date,
            @PathVariable final UUID stateID
    ) {
        return workingStateService.findWorkingStateByDay(stateID, date);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
    @InsideRequestGet(uri = "/v1/working/current/day/state/{stateID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayState(
            @PathVariable final UUID stateID
    ) {
        return workingStateService.findWorkingCurrentDayState(stateID);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
    @InsideRequestGet(uri = "/v1/working/day/{date}/city/{cityID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCityByDay(
            @PathVariable final LocalDate date,
            @PathVariable final UUID cityID
    ) {
        return workingCityService.findWorkingCityByDay(cityID, date);
    }

    @PreAuthorize("hasAnyRole('DayOff.Read','DayOff.WorkingState.Read')")
    @InsideRequestGet(uri = "/v1/working/current/day/city/{cityID}", httpCode = HttpStatus.OK, nameCache = "DAYOFF_MARKER_WORKING")
    public InsideSoftwaresResponseDTO<WorkingCurrentDayResponseDTO> findWorkingCurrentDayCity(
            @PathVariable final UUID cityID
    ) {
        return workingCityService.findWorkingCurrentDayCity(cityID);
    }

}
