package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.CityRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.city.CityResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderCity;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeAcronymStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityCodeStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNameStateExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.City;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.State;
import br.com.sawcunha.dayoffmarker.mapper.CityMapper;
import br.com.sawcunha.dayoffmarker.repository.CityRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CityService;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.StateService;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CityServiceBean implements CityService {

    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final StateService stateService;
    private final CityMapper cityMapper;
    private final Validator<Long, CityRequestDTO> cityValidator;

    @Transactional(readOnly = true)
    @Override
    public DayOffMarkerResponse<List<CityResponseDTO>> findAll(
            final Long stateID,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderCity orderCity
    ) throws Exception {

        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderCity);

        Page<City> cities;
        if(Objects.nonNull(stateID)){
            cities = cityRepository.findCityByStateID(stateID,pageable);
        } else {
            Country country = countryService.findCountryDefault();
            cities = cityRepository.findCityByCountry(country,pageable);
        }

        return DayOffMarkerResponse.<List<CityResponseDTO>>builder()
                .data(cityMapper.toDTOs(cities.getContent()))
                .paginated(
                        PaginationUtils.createPaginated(
                                cities.getTotalPages(),
                                cities.getTotalElements(),
                                sizePerPage
                        )
                )
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public DayOffMarkerResponse<CityResponseDTO> findById(final Long cityID) throws Exception {
        City city = cityRepository.findById(cityID).orElseThrow(CityNotExistException::new);
        return DayOffMarkerResponse.<CityResponseDTO>builder()
                .data(cityMapper.toDTO(city))
                .build();
    }

    @Transactional(rollbackFor = {
            StateNotExistException.class,
            CityCodeStateExistException.class,
            CityCodeAcronymStateExistException.class,
            CityNameStateExistException.class
    })
    @Override
    public DayOffMarkerResponse<CityResponseDTO> save(final @Valid CityRequestDTO cityRequestDTO) throws Exception {
        cityValidator.validator(cityRequestDTO);

        State state = stateService.findStateByStateId(cityRequestDTO.getStateID());

        City city = City.builder()
                .name(cityRequestDTO.getName())
                .acronym(cityRequestDTO.getAcronym())
                .code(cityRequestDTO.getCode())
                .name(cityRequestDTO.getName())
                .state(state)
                .build();

        city = cityRepository.save(city);

        return DayOffMarkerResponse.<CityResponseDTO>builder()
                .data(cityMapper.toDTO(city))
                .build();
    }

    @Transactional(rollbackFor = {
            StateNotExistException.class,
            CityNotExistException.class,
            CityCodeStateExistException.class,
            CityCodeAcronymStateExistException.class,
            CityNameStateExistException.class
    })
    @Override
    public DayOffMarkerResponse<CityResponseDTO> update(
            final Long cityID,
            final @Valid CityRequestDTO cityRequestDTO
    ) throws Exception {
        cityValidator.validator(cityID, cityRequestDTO);

        City city = cityRepository.getById(cityID);

        if(!city.getState().getId().equals(cityRequestDTO.getStateID())){
            State state = stateService.findStateByStateId(cityRequestDTO.getStateID());
            city.setState(state);
        }

        city.setName(cityRequestDTO.getName());
        city.setCode(cityRequestDTO.getCode());
        city.setAcronym(cityRequestDTO.getAcronym());

        city = cityRepository.save(city);

        return DayOffMarkerResponse.<CityResponseDTO>builder()
                .data(cityMapper.toDTO(city))
                .build();
    }
}
