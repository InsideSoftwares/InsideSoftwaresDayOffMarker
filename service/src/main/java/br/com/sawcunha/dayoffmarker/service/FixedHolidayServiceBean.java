package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayUpdateRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderFixedHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayMonthInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
import br.com.sawcunha.dayoffmarker.mapper.FixedHolidayMapper;
import br.com.sawcunha.dayoffmarker.repository.FixedHolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.FixedHolidayService;
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
@Transactional(readOnly = true)
class FixedHolidayServiceBean implements FixedHolidayService {

    private final CountryService countryService;
    private final FixedHolidayRepository fixedHolidayRepository;
	private final Validator<Long, FixedHolidayRequestDTO> fixedHolidayValidator;
	private final Validator<Long, FixedHolidayUpdateRequestDTO> fixedHolidayUpdateValidator;
    private final FixedHolidayMapper fixedHolidayMapper;

    @Override
    public DayOffMarkerResponse<List<FixedHolidayResponseDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderFixedHoliday orderFixedHoliday
    ) throws DayOffMarkerGenericException {

        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderFixedHoliday);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByCountry(country,pageable);
        return DayOffMarkerResponse.<List<FixedHolidayResponseDTO>>builder()
                .data(fixedHolidayMapper.toDTOs(fixedHolidays.getContent()))
                .paginated(
                        PaginationUtils.createPaginated(
                                fixedHolidays.getTotalPages(),
                                fixedHolidays.getTotalElements(),
                                sizePerPage
                        )
                )
                .build();

    }

    @Override
    public DayOffMarkerResponse<FixedHolidayResponseDTO> findById(final Long fixedHolidayID) throws FixedHolidayNotExistException {
        FixedHoliday fixedHoliday = fixedHolidayRepository
                .findById(fixedHolidayID)
                .orElseThrow(FixedHolidayNotExistException::new);

        return DayOffMarkerResponse.<FixedHolidayResponseDTO>builder()
                .data(fixedHolidayMapper.toDTO(fixedHoliday))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class
    })
    @Override
    public DayOffMarkerResponse<FixedHolidayResponseDTO> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO) throws DayOffMarkerGenericException {
        fixedHolidayValidator.validator(fixedHolidayRequestDTO);

        Country country = countryService.findCountryByCountryId(fixedHolidayRequestDTO.getCountryId());

        boolean isOptional = Objects.nonNull(fixedHolidayRequestDTO.getIsOptional()) ?
                fixedHolidayRequestDTO.getIsOptional() : false;

        FixedHoliday fixedHoliday = FixedHoliday.builder()
                .name(fixedHolidayRequestDTO.getName())
                .description(fixedHolidayRequestDTO.getDescription())
                .day(fixedHolidayRequestDTO.getDay())
                .month(fixedHolidayRequestDTO.getMonth())
                .isOptional(isOptional)
                .fromTime(fixedHolidayRequestDTO.getFromTime())
                .country(country)
                .build();

        fixedHoliday = fixedHolidayRepository.save(fixedHoliday);

        return DayOffMarkerResponse.<FixedHolidayResponseDTO>builder()
                .data(fixedHolidayMapper.toDTO(fixedHoliday))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class,
            FixedHolidayNotExistException.class
    })
    @Override
    public DayOffMarkerResponse<FixedHolidayResponseDTO> update(
            Long fixedHolidayID,
            final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) throws DayOffMarkerGenericException {
	fixedHolidayUpdateValidator.validator(fixedHolidayID,fixedHolidayRequestDTO);

        FixedHoliday fixedHoliday = fixedHolidayRepository.getById(fixedHolidayID);

        fixedHoliday.setName(fixedHolidayRequestDTO.getName());
        fixedHoliday.setDescription(fixedHolidayRequestDTO.getDescription());
        fixedHoliday.setOptional(fixedHolidayRequestDTO.getIsOptional());
        fixedHoliday.setFromTime(fixedHolidayRequestDTO.getFromTime());

        fixedHoliday = fixedHolidayRepository.save(fixedHoliday);

        return DayOffMarkerResponse.<FixedHolidayResponseDTO>builder()
                .data(fixedHolidayMapper.toDTO(fixedHoliday))
                .build();
    }

	@Override
	public List<FixedHoliday> findAllByCountry() throws DayOffMarkerGenericException {
		Country country = countryService.findCountryDefault();
		return fixedHolidayRepository.findAllByCountry(country);
	}

	@Override
	public FixedHoliday findFixedHolidayById(Long fixedHolidayID) throws FixedHolidayNotExistException {
		return fixedHolidayRepository.findById(fixedHolidayID)
				.orElseThrow(FixedHolidayNotExistException::new);
	}
}
