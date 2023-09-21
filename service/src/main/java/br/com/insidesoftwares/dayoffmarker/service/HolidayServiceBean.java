package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.request.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.HolidaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.service.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.HolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class HolidayServiceBean implements HolidayService {
    
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final Validator<Long, HolidayRequestDTO> holidayValidator;
    private final DayService dayService;

    @InsideAudit
    @Override
	public InsideSoftwaresResponseDTO<List<HolidayResponseDTO>> findAll(
			final LocalDate startDate,
			final LocalDate endDate,
			InsidePaginationFilterDTO paginationFilter
	) {

		Pageable pageable = PaginationUtils.createPageable(paginationFilter, eOrderHoliday.ID);

		Specification<Holiday> holidaySpecification = HolidaySpecification.findAllByStartDateAndEndDate(startDate, endDate);

		Page<Holiday> holidays = holidayRepository.findAll(holidaySpecification, pageable);

		return InsideSoftwaresResponseDTO.<List<HolidayResponseDTO>>builder()
				.data(holidayMapper.toDTOs(holidays.getContent()))
				.insidePaginatedDTO(
						PaginationUtils.createPaginated(
							holidays.getTotalPages(),
							holidays.getTotalElements(),
							holidays.getContent().size(),
							paginationFilter.getSizePerPage()
						)
				)
				.build();
	}

    @InsideAudit
    @Override
    public InsideSoftwaresResponseDTO<HolidayResponseDTO> findById(final Long holidayID) {
        Holiday holiday = findHolidayById(holidayID);
        return InsideSoftwaresResponseDTO.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
		DayNotExistException.class,
		HolidayDayExistException.class,
		HolidayFromTimeNotInformedException.class
	})
	@Override
	public InsideSoftwaresResponseDTO<List<Long>> saveInBatch(final HolidayBatchRequestDTO holidayBatchRequestDTO) {
        List<Long> holidayIDs = new ArrayList<>();
		holidayBatchRequestDTO.daysId().forEach(dayID -> {
			HolidayRequestDTO holidayRequestDTO = holidayMapper.toHolidayResponseDTO(holidayBatchRequestDTO, dayID);
			InsideSoftwaresResponseDTO<Long> response = save(holidayRequestDTO);
            holidayIDs.add(response.getData());
		});

        return InsideSoftwaresResponseDTO.<List<Long>>builder().data(holidayIDs).build();
	}

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<Long> save(final HolidayRequestDTO holidayRequestDTO) {
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
                .nationalHoliday(holidayRequestDTO.nationalHoliday())
                .build();

        holiday = holidayRepository.save(holiday);

        dayService.defineDayIsHoliday(holidayRequestDTO.dayId());

        return InsideSoftwaresResponseDTO.<Long>builder().data(holiday.getId()).build();
    }

    @InsideAudit
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
            dayService.defineDayIsHoliday(holiday.getDay().getId());
            Day day = dayService.findDayByID(holidayRequestDTO.dayId());
            holiday.setDay(day);
        }

        holiday.setName(holidayRequestDTO.name());
        holiday.setDescription(holidayRequestDTO.description());
        holiday.setHolidayType(holidayRequestDTO.holidayType());
        holiday.setFromTime(holidayRequestDTO.fromTime());
		holiday.setAutomaticUpdate(false);
		holiday.setOptional(holidayRequestDTO.optional());
        holiday.setNationalHoliday(holidayRequestDTO.nationalHoliday());
        holidayRepository.save(holiday);

        dayService.defineDayIsHoliday(holidayRequestDTO.dayId());
    }

    @InsideAudit
    @Transactional(rollbackFor = {
		DayNotExistException.class,
		HolidayDayExistException.class,
		HolidayFromTimeNotInformedException.class
	})
	@Override
	public void saveHoliday(final HolidayCreateRequestDTO holidayCreateRequestDTO) {
		Day day = dayService.findDayByID(holidayCreateRequestDTO.dayId());

		Holiday holiday = Holiday.builder()
			.name(holidayCreateRequestDTO.name())
			.description(holidayCreateRequestDTO.description())
			.holidayType(holidayCreateRequestDTO.holidayType())
			.fromTime(holidayCreateRequestDTO.fromTime())
			.day(day)
			.automaticUpdate(true)
			.fixedHolidayID(holidayCreateRequestDTO.fixedHolidayID())
            .nationalHoliday(holidayCreateRequestDTO.nationalHoliday())
			.build();
		holidayRepository.save(holiday);

		dayService.defineDayIsHoliday(holidayCreateRequestDTO.dayId());
	}

    @InsideAudit
    @Override
	public Holiday findHolidayById(Long holidayID) {
		return holidayRepository.findById(holidayID).orElseThrow(HolidayNotExistException::new);
	}
}
