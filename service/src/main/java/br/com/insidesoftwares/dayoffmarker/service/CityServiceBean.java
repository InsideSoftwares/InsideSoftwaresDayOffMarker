package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.city.CityRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.CityHolidayPK;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.city.CityMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.CityService;
import br.com.insidesoftwares.dayoffmarker.specification.service.CountryService;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.StateService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CityServiceBean implements CityService {

    private final CityRepository cityRepository;
    private final CityHolidayRepository cityHolidayRepository;
    private final CountryService countryService;
    private final StateService stateService;
    private final HolidayService holidayService;
    private final CityMapper cityMapper;
    private final Validator<Long, CityRequestDTO> cityValidator;

    @InsideAudit(description = "Search all cities")
    @Override
    public InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(
            final Long stateID,
            final InsidePaginationFilterDTO paginationFilter
    ) {

        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderCity.ID);

        Page<City> cities;
        if (Objects.nonNull(stateID)) {
            cities = cityRepository.findCityByStateID(stateID, pageable);
        } else {
            Country country = countryService.findCountryDefault();
            cities = cityRepository.findCityByCountry(country, pageable);
        }

        return InsideSoftwaresResponseDTO.<List<CityResponseDTO>>builder()
                .data(cityMapper.toDTOs(cities.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
                                cities.getTotalPages(),
                                cities.getTotalElements(),
                                cities.getContent().size(),
                                paginationFilter.getSizePerPage()
                        )
                )
                .build();
    }

    @InsideAudit(description = "Find City by ID")
    @Override
    public InsideSoftwaresResponseDTO<CityResponseDTO> findById(final Long cityID) {
        City city = findCityById(cityID);
        return InsideSoftwaresResponseDTO.<CityResponseDTO>builder()
                .data(cityMapper.toFullDTO(city))
                .build();
    }

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

        State state = stateService.findStateByStateId(cityRequestDTO.stateID());

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
            State state = stateService.findStateByStateId(cityRequestDTO.stateID());
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
        City city = findCityById(cityID);

        cityHolidayRequestDTO.holidaysId().forEach(holidayId -> {
            Holiday holiday = holidayService.findHolidayById(holidayId);
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
        City city = findCityById(cityID);

        cityHolidayDeleteRequestDTO.holidaysId().forEach(holidayId -> {
            Optional<CityHoliday> cityHolidayOptional = city.containsHoliday(holidayId);

            cityHolidayOptional.ifPresent(cityHoliday -> {
                cityHolidayRepository.delete(cityHoliday);
                city.deleteHoliday(holidayId);
            });
        });

        cityRepository.save(city);
    }

    @InsideAudit(description = "Find City by ID")
    @Override
    public City findCityById(final Long cityID) {
        return cityRepository.findCityById(cityID).orElseThrow(CityNotExistException::new);
    }

    @InsideAudit(description = "Search city with all its information by ID")
    @Override
    public City findCityFullHolidayById(final Long cityID) {
        return cityRepository.findCityFullHolidayById(cityID).orElseThrow(CityNotExistException::new);
    }
}
