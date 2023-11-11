package br.com.insidesoftwares.dayoffmarker.service.holiday;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayBatchRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayCreateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.HolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.holiday.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.day.DaySearchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.day.DayService;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.HolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class HolidayServiceBean implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;
    private final Validator<UUID, HolidayRequestDTO> holidayValidator;
    private final DaySearchService daySearchService;
    private final DayService dayService;

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<List<UUID>> saveInBatch(final HolidayBatchRequestDTO holidayBatchRequestDTO) {
        List<UUID> holidayIDs = new ArrayList<>();
        holidayBatchRequestDTO.daysId().forEach(dayID -> {
            HolidayRequestDTO holidayRequestDTO = holidayMapper.toHolidayResponseDTO(holidayBatchRequestDTO, dayID);
            InsideSoftwaresResponseDTO<UUID> response = save(holidayRequestDTO);
            holidayIDs.add(response.getData());
        });

        return InsideSoftwaresResponseDTO.<List<UUID>>builder().data(holidayIDs).build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<UUID> save(final HolidayRequestDTO holidayRequestDTO) {
        holidayValidator.validator(holidayRequestDTO);

        Day day = daySearchService.findDayByID(holidayRequestDTO.dayId());

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

        return InsideSoftwaresResponseDTO.<UUID>builder().data(holiday.getId()).build();
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
            final UUID holidayID,
            final HolidayRequestDTO holidayRequestDTO
    ) {
        holidayValidator.validator(holidayID, holidayRequestDTO);

        Holiday holiday = holidayRepository.getReferenceById(holidayID);
        if (!holiday.getDay().getId().equals(holidayRequestDTO.dayId())) {
            dayService.defineDayIsHoliday(holiday.getDay().getId());
            Day day = daySearchService.findDayByID(holidayRequestDTO.dayId());
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
        Day day = daySearchService.findDayByID(holidayCreateRequestDTO.dayId());

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
}
