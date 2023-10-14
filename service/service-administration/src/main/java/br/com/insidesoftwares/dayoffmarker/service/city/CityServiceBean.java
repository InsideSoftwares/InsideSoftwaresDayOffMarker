package br.com.insidesoftwares.dayoffmarker.service.city;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHolidayPK;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.CitySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.HolidaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.StateSearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.CityService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class CityServiceBean implements CityService {

    private final CityRepository cityRepository;
    private final CitySearchService citySearchService;
    private final CityHolidayRepository cityHolidayRepository;
    private final StateSearchService stateSearchService;
    private final HolidaySearchService holidaySearchService;
    private final Validator<Long, CityRequestDTO> cityValidator;

    @InsideAudit(description = "Save the City")
    @Transactional(rollbackFor = {
            StateNotExistException.class,
            CityCodeStateExistException.class,
            CityCodeAcronymStateExistException.class,
            CityNameStateExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<Long> save(final @Valid CityRequestDTO cityRequestDTO) {
        cityValidator.validator(cityRequestDTO);

        State state = stateSearchService.findStateByStateId(cityRequestDTO.stateID());

        City city = City.builder()
                .name(cityRequestDTO.name())
                .acronym(cityRequestDTO.acronym())
                .code(cityRequestDTO.code())
                .state(state)
                .build();

        city = cityRepository.save(city);

        return InsideSoftwaresResponseDTO.<Long>builder().data(city.getId()).build();
    }

    @InsideAudit(description = "Update the city by ID")
    @Transactional(rollbackFor = {
            StateNotExistException.class,
            CityNotExistException.class,
            CityCodeStateExistException.class,
            CityCodeAcronymStateExistException.class,
            CityNameStateExistException.class
    })
    @Override
    public void update(
            final Long cityID,
            final @Valid CityRequestDTO cityRequestDTO
    ) {
        cityValidator.validator(cityID, cityRequestDTO);

        City city = cityRepository.getReferenceById(cityID);

        if (!city.getState().getId().equals(cityRequestDTO.stateID())) {
            State state = stateSearchService.findStateByStateId(cityRequestDTO.stateID());
            city.setState(state);
        }

        city.setName(cityRequestDTO.name());
        city.setCode(cityRequestDTO.code());
        city.setAcronym(cityRequestDTO.acronym());

        cityRepository.save(city);
    }

    @InsideAudit(description = "Add holiday to city")
    @Transactional(rollbackFor = {
            CityNotExistException.class,
            HolidayNotExistException.class,
            Exception.class
    })
    @Override
    public void addCityHoliday(
            final Long cityID,
            @Valid final CityHolidayRequestDTO cityHolidayRequestDTO
    ) {
        City city = citySearchService.findCityById(cityID);

        cityHolidayRequestDTO.holidaysId().forEach(holidayId -> {
            Holiday holiday = holidaySearchService.findHolidayById(holidayId);
            CityHolidayPK cityHolidayPK = CityHolidayPK.builder()
                    .cityId(city.getId())
                    .holidayId(holiday.getId())
                    .build();

            CityHoliday cityHoliday = new CityHoliday(cityHolidayPK, cityHolidayRequestDTO.isHoliday());

            city.addHoliday(cityHoliday);
        });

        cityRepository.save(city);
    }

    @InsideAudit(description = "Remove holiday to city")
    @Transactional(rollbackFor = {
            CityNotExistException.class,
            Exception.class
    })
    @Override
    public void deleteCityHoliday(
            final Long cityID,
            @Valid final CityHolidayDeleteRequestDTO cityHolidayDeleteRequestDTO
    ) {
        City city = citySearchService.findCityById(cityID);

        cityHolidayDeleteRequestDTO.holidaysId().forEach(holidayId -> {
            Optional<CityHoliday> cityHolidayOptional = city.containsHoliday(holidayId);

            cityHolidayOptional.ifPresent(cityHoliday -> {
                cityHolidayRepository.delete(cityHoliday);
                city.deleteHoliday(holidayId);
            });
        });

        cityRepository.save(city);
    }
}
