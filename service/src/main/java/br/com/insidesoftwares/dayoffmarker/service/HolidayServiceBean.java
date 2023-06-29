package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.entity.Day;
import br.com.insidesoftwares.dayoffmarker.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.mapper.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.repository.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class HolidayServiceBean implements HolidayService {
    
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final Validator<Long, HolidayRequestDTO> holidayValidator;
    private final DayService dayService;

	@Override
	public InsideSoftwaresResponse<List<HolidayResponseDTO>> findAll(
			final LocalDate startDate,
			final LocalDate endDate,
			PaginationFilter<eOrderHoliday> paginationFilter
	) {

		Pageable pageable = PaginationUtils.createPageable(paginationFilter);

		Page<Holiday> holidays;
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {

			if (startDate.isAfter(endDate)) {
				throw new StartDateAfterEndDateException();
			}

			holidays = holidayRepository.findAllByStartDateAndEndDate(
					startDate,
					endDate,
					pageable
			);
		} else {
			holidays = holidayRepository.findAll(pageable);
		}

		return InsideSoftwaresResponse.<List<HolidayResponseDTO>>builder()
				.data(holidayMapper.toDTOs(holidays.getContent()))
				.paginatedDTO(
						PaginationUtils.createPaginated(
							holidays.getTotalPages(),
							holidays.getTotalElements(),
							holidays.getContent().size(),
							paginationFilter.getSizePerPage()
						)
				)
				.build();
	}

    @Override
    public InsideSoftwaresResponse<HolidayResponseDTO> findById(final Long holidayID) {
        Holiday holiday = findHolidayById(holidayID);
        return InsideSoftwaresResponse.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }

	@Transactional(rollbackFor = {
		DayNotExistException.class,
		HolidayDayExistException.class,
		HolidayFromTimeNotInformedException.class
	})
	@Override
	public void saveInBatch(final HolidayBatchRequestDTO holidayBatchRequestDTO) {
		holidayBatchRequestDTO.daysId().forEach(dayID -> {
			HolidayRequestDTO holidayRequestDTO = holidayMapper.toHolidayResponseDTO(holidayBatchRequestDTO, dayID);
			save(holidayRequestDTO);
		});

	}

    @Transactional(rollbackFor = {
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public void save(final HolidayRequestDTO holidayRequestDTO) {
        holidayValidator.validator(holidayRequestDTO);

        Day day = dayService.findDayByID(holidayRequestDTO.dayId());

        Holiday holiday = Holiday.builder()
                .name(holidayRequestDTO.name())
                .description(holidayRequestDTO.description())
                .holidayType(holidayRequestDTO.holidayType())
                .fromTime(holidayRequestDTO.fromTime())
                .day(day)
				.optional(holidayRequestDTO.optional())
				.automaticUpdate(false)
                .build();

        holidayRepository.save(holiday);

        dayService.setDayHoliday(holidayRequestDTO.dayId(), true);
    }

    @Transactional(rollbackFor = {
            HolidayNotExistException.class,
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public void update(
            final Long holidayID,
            final HolidayRequestDTO holidayRequestDTO
    ) {
        holidayValidator.validator(holidayID, holidayRequestDTO);

        Holiday holiday = holidayRepository.getReferenceById(holidayID);
        if(!holiday.getDay().getId().equals(holidayRequestDTO.dayId())) {
            dayService.setDayHoliday(holiday.getDay().getId(), false);
            Day day = dayService.findDayByID(holidayRequestDTO.dayId());
            holiday.setDay(day);
        }

        holiday.setName(holidayRequestDTO.name());
        holiday.setDescription(holidayRequestDTO.description());
        holiday.setHolidayType(holidayRequestDTO.holidayType());
        holiday.setFromTime(holidayRequestDTO.fromTime());
		holiday.setAutomaticUpdate(false);
		holiday.setOptional(holidayRequestDTO.optional());
        holidayRepository.save(holiday);

        dayService.setDayHoliday(holidayRequestDTO.dayId(), true);
    }

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			HolidayDayExistException.class,
			HolidayFromTimeNotInformedException.class
	})
	@Override
	public void saveHoliday(final HolidayRequestDTO holidayRequestDTO) {

		Optional<Holiday> optionalHoliday = holidayRepository.findByDayID(holidayRequestDTO.dayId());
		Day day = dayService.findDayByID(holidayRequestDTO.dayId());
		Holiday holiday;
		if(optionalHoliday.isPresent()){
			holidayValidator.validator(optionalHoliday.get().getId(), holidayRequestDTO);
			holiday = Holiday.builder()
					.name(holidayRequestDTO.name())
					.description(holidayRequestDTO.description())
					.holidayType(holidayRequestDTO.holidayType())
					.fromTime(holidayRequestDTO.fromTime())
					.day(day)
					.automaticUpdate(true)
					.build();
			holiday.setId(
					optionalHoliday.get().getId()
			);
		} else {
			holidayValidator.validator(holidayRequestDTO);
			holiday = Holiday.builder()
					.name(holidayRequestDTO.name())
					.description(holidayRequestDTO.description())
					.holidayType(holidayRequestDTO.holidayType())
					.fromTime(holidayRequestDTO.fromTime())
					.day(day)
					.automaticUpdate(true)
					.build();
		}
		holidayRepository.save(holiday);
		dayService.setDayHoliday(holidayRequestDTO.dayId(), true);
	}

	@Override
	public Holiday findHolidayById(Long holidayID) {
		return holidayRepository.findById(holidayID).orElseThrow(HolidayNotExistException::new);
	}
}
