package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayDeleteRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.state.StateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.state.StateResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderState;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.city.CityNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNameCountryAcronymExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.state.StateNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.Country;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.entity.state.StateHoliday;
import br.com.insidesoftwares.dayoffmarker.entity.state.StateHolidayPK;
import br.com.insidesoftwares.dayoffmarker.mapper.state.StateMapper;
import br.com.insidesoftwares.dayoffmarker.repository.state.StateHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.repository.state.StateRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class StateServiceBean implements StateService {

    private final StateRepository stateRepository;
	private final StateHolidayRepository stateHolidayRepository;
    private final CountryService countryService;
	private final HolidayService holidayService;
    private final Validator<Long, StateRequestDTO> stateValidator;
    private final StateMapper stateMapper;

    @Override
    public InsideSoftwaresResponse<List<StateResponseDTO>> findAll(
            final String nameCountry,
			final PaginationFilter<eOrderState> paginationFilter
    ) {

        Pageable pageable = PaginationUtils.createPageable(paginationFilter);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<State> states = stateRepository.findAllByCountry(country, pageable);

        return InsideSoftwaresResponse.<List<StateResponseDTO>>builder()
                .data(stateMapper.toDTOs(states.getContent()))
                .paginatedDTO(
                        PaginationUtils.createPaginated(
                            states.getTotalPages(),
                            states.getTotalElements(),
							states.getContent().size(),
							paginationFilter.getSizePerPage()
                    )
                )
                .build();
    }

    @Override
    public InsideSoftwaresResponse<StateResponseDTO> findById(final Long stateID) {
        State state = stateRepository.findStateById(stateID).orElseThrow(StateNotExistException::new);
        return InsideSoftwaresResponse.<StateResponseDTO>builder()
                .data(stateMapper.toFullDTO(state))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class,
            StateNotExistException.class
    })
    @Override
    public void save(StateRequestDTO stateRequestDTO) {
        stateValidator.validator(stateRequestDTO);

        Country country = countryService.findCountryByCountryId(stateRequestDTO.countryId());

        State state = State.builder()
                .name(stateRequestDTO.name())
                .acronym(stateRequestDTO.acronym())
				.code(stateRequestDTO.code())
                .country(country)
                .build();

        stateRepository.save(state);
    }

    @Transactional(rollbackFor = {
            CountryNameInvalidException.class,
            StateNameCountryAcronymExistException.class,
            StateCountryAcronymExistException.class
    })
    @Override
    public void update(Long stateID, StateRequestDTO stateRequestDTO) {
        stateValidator.validator(stateID, stateRequestDTO);
        State state = stateRepository.getReferenceById(stateID);

        if(!stateRequestDTO.countryId().equals(state.getCountry().getId())){
            Country country = countryService.findCountryByCountryId(stateRequestDTO.countryId());
            state.setCountry(country);
        }

        state.setName(stateRequestDTO.name());
        state.setAcronym(stateRequestDTO.acronym());
		state.setCode(stateRequestDTO.code());
        stateRepository.save(state);
    }

    @Override
    public State findStateByStateId(final Long stateId)  {
        Optional<State> optionalState = stateRepository.findById(stateId);
        return optionalState.orElseThrow(StateNotExistException::new);
    }

	@Transactional(rollbackFor = {
		CityNotExistException.class,
		HolidayNotExistException.class,
		Exception.class
	})
	@Override
	public void addStateHoliday(
		final Long stateID,
		@Valid final StateHolidayRequestDTO stateHolidayRequestDTO
	) {
		State state = stateRepository.findById(stateID).orElseThrow(StateNotExistException::new);

		stateHolidayRequestDTO.holidaysId().forEach(holidayId -> {
			Holiday holiday = holidayService.findHolidayById(holidayId);
			StateHolidayPK stateHolidayPK = StateHolidayPK.builder()
				.stateId(state.getId())
				.holidayId(holiday.getId())
				.build();

			StateHoliday stateHoliday = new StateHoliday(stateHolidayPK, stateHolidayRequestDTO.isHoliday());

			state.addHoliday(stateHoliday);
		});

		stateRepository.save(state);
	}

	@Transactional(rollbackFor = {
		StateNotExistException.class,
		Exception.class
	})
	@Override
	public void deleteStateHoliday(
		final Long stateID,
		@Valid final StateHolidayDeleteRequestDTO stateHolidayDeleteRequestDTO
	) {
		State state = stateRepository.findById(stateID).orElseThrow(StateNotExistException::new);

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
