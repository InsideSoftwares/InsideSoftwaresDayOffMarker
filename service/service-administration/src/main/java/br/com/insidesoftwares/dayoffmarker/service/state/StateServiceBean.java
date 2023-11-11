package br.com.insidesoftwares.dayoffmarker.service.state;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.StateHolidayPK;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.country.CountrySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.holiday.HolidaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.search.state.StateSearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.state.StateService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class StateServiceBean implements StateService {

    private final StateRepository stateRepository;
    private final StateHolidayRepository stateHolidayRepository;
    private final CountrySearchService countrySearchService;
    private final HolidaySearchService holidaySearchService;
    private final StateSearchService stateSearchService;
    private final Validator<UUID, StateRequestDTO> stateValidator;

    @InsideAudit
    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class,
            StateNotExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<UUID> save(StateRequestDTO stateRequestDTO) {
        stateValidator.validator(stateRequestDTO);

        Country country = countrySearchService.findCountryByCountryId(stateRequestDTO.countryId());

        State state = State.builder()
                .name(stateRequestDTO.name())
                .acronym(stateRequestDTO.acronym())
                .code(stateRequestDTO.code())
                .country(country)
                .build();

        state = stateRepository.save(state);

        return InsideSoftwaresResponseDTO.<UUID>builder().data(state.getId()).build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class
    })
    @Override
    public void update(UUID stateID, StateRequestDTO stateRequestDTO) {
        stateValidator.validator(stateID, stateRequestDTO);

        State state = stateRepository.getReferenceById(stateID);

        if (!stateRequestDTO.countryId().equals(state.getCountry().getId())) {
            Country country = countrySearchService.findCountryByCountryId(stateRequestDTO.countryId());
            state.setCountry(country);
        }
        state.setName(stateRequestDTO.name());
        state.setAcronym(stateRequestDTO.acronym());
        state.setCode(stateRequestDTO.code());

        stateRepository.save(state);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            CityNotExistException.class,
            HolidayNotExistException.class,
            Exception.class
    })
    @Override
    public void addStateHoliday(
            final UUID stateID,
            @Valid final StateHolidayRequestDTO stateHolidayRequestDTO
    ) {
        State state = stateSearchService.findStateByStateId(stateID);

        stateHolidayRequestDTO.holidaysId().forEach(holidayId -> {
            Holiday holiday = holidaySearchService.findHolidayById(holidayId);
            StateHolidayPK stateHolidayPK = StateHolidayPK.builder()
                    .stateId(state.getId())
                    .holidayId(holiday.getId())
                    .build();

            StateHoliday stateHoliday = new StateHoliday(stateHolidayPK, stateHolidayRequestDTO.isHoliday());

            state.addHoliday(stateHoliday);
        });

        stateRepository.save(state);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            StateNotExistException.class,
            Exception.class
    })
    @Override
    public void deleteStateHoliday(
            final UUID stateID,
            @Valid final StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO
    ) {
        State state = stateSearchService.findStateByStateId(stateID);

        stateHolidayDeleteRequestDTO.holidaysId().forEach(holidayId -> {
            Optional<StateHoliday> stateHolidayOptional = state.containsHoliday(holidayId);

            stateHolidayOptional.ifPresent(stateHoliday -> {
                stateHolidayRepository.delete(stateHoliday);
                state.deleteHoliday(holidayId);
            });
        });

        stateRepository.save(state);
    }
}
