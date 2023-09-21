package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderFixedHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayMonthInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.FixedHolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.FixedHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class FixedHolidayServiceBean implements FixedHolidayService {

    private final FixedHolidayRepository fixedHolidayRepository;
	private final Validator<Long, FixedHolidayRequestDTO> fixedHolidayValidator;
	private final Validator<Long, FixedHolidayUpdateRequestDTO> fixedHolidayUpdateValidator;
    private final FixedHolidayMapper fixedHolidayMapper;

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<List<FixedHolidayResponseDTO>> findAll(
			final InsidePaginationFilterDTO paginationFilter
    ) {

		Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderFixedHoliday.ID);

        Page<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAll(pageable);
        return InsideSoftwaresResponseDTO.<List<FixedHolidayResponseDTO>>builder()
                .data(fixedHolidayMapper.toDTOs(fixedHolidays.getContent()))
                .insidePaginatedDTO(
                        PaginationUtils.createPaginated(
							fixedHolidays.getTotalPages(),
							fixedHolidays.getTotalElements(),
							fixedHolidays.getContent().size(),
							paginationFilter.getSizePerPage()
                        )
                )
                .build();

    }

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<FixedHolidayResponseDTO> findById(final Long fixedHolidayID) throws FixedHolidayNotExistException {
        FixedHoliday fixedHoliday = fixedHolidayRepository
                .findById(fixedHolidayID)
                .orElseThrow(FixedHolidayNotExistException::new);

        return InsideSoftwaresResponseDTO.<FixedHolidayResponseDTO>builder()
                .data(fixedHolidayMapper.toDTO(fixedHoliday))
                .build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class
    })
    @Override
    public void save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO) {
        fixedHolidayValidator.validator(fixedHolidayRequestDTO);

        boolean isOptional = fixedHolidayRequestDTO.isOptional();

        FixedHoliday fixedHoliday = FixedHoliday.builder()
                .name(fixedHolidayRequestDTO.name())
                .description(fixedHolidayRequestDTO.description())
                .day(fixedHolidayRequestDTO.day())
                .month(fixedHolidayRequestDTO.month())
                .isOptional(isOptional)
                .fromTime(fixedHolidayRequestDTO.fromTime())
				.isEnable(Objects.nonNull(fixedHolidayRequestDTO.isEnable()) ? fixedHolidayRequestDTO.isEnable() : true)
                .build();

        fixedHolidayRepository.save(fixedHoliday);
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class,
            FixedHolidayNotExistException.class
    })
    @Override
    public void update(
            final Long fixedHolidayID,
            final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) {
		fixedHolidayUpdateValidator.validator(fixedHolidayID,fixedHolidayRequestDTO);

        FixedHoliday fixedHoliday = fixedHolidayRepository.getReferenceById(fixedHolidayID);

        fixedHoliday.setName(fixedHolidayRequestDTO.name());
        fixedHoliday.setDescription(fixedHolidayRequestDTO.description());
        fixedHoliday.setOptional(fixedHolidayRequestDTO.isOptional());
        fixedHoliday.setFromTime(fixedHolidayRequestDTO.fromTime());
		fixedHoliday.setEnable(Objects.nonNull(fixedHolidayRequestDTO.isEnable()) ? fixedHolidayRequestDTO.isEnable() : fixedHoliday.isEnable());

        fixedHolidayRepository.save(fixedHoliday);
    }

    @InsideAudit
    @Override
	public List<FixedHoliday> findAllByEnable(final boolean isEnable) {
		return fixedHolidayRepository.findAllByIsEnable(isEnable);
	}

    @InsideAudit
    @Override
	public FixedHoliday findFixedHolidayById(Long fixedHolidayID) throws FixedHolidayNotExistException {
		return fixedHolidayRepository.findById(fixedHolidayID)
				.orElseThrow(FixedHolidayNotExistException::new);
	}
}
