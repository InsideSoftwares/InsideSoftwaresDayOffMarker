package br.com.insidesoftwares.dayoffmarker.service.city;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.city.CityResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.city.CityMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.city.CitySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.country.CountrySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CitySearchServiceBean implements CitySearchService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CountrySearchService countrySearchService;

    @InsideAudit(description = "Search all cities")
    @Override
    public InsideSoftwaresResponseDTO<List<CityResponseDTO>> findAll(
            final UUID stateID,
            final InsidePaginationFilterDTO paginationFilter
    ) {

        Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderCity.ID);

        Page<City> cities;
        if (Objects.nonNull(stateID)) {
            cities = cityRepository.findCityByStateID(stateID, pageable);
        } else {
            Country country = countrySearchService.findCountryDefault();
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
    public InsideSoftwaresResponseDTO<CityResponseDTO> findById(final UUID cityID) {
        City city = findCityById(cityID);
        return InsideSoftwaresResponseDTO.<CityResponseDTO>builder()
                .data(cityMapper.toFullDTO(city))
                .build();
    }

    @InsideAudit(description = "Find City by ID")
    @Override
    public City findCityById(final UUID cityID) {
        return cityRepository.findCityById(cityID).orElseThrow(CityNotExistException::new);
    }

    @InsideAudit(description = "Search city with all its information by ID")
    @Override
    public City findCityFullHolidayById(final UUID cityID) {
        return cityRepository.findCityFullHolidayById(cityID).orElseThrow(CityNotExistException::new);
    }
}
