package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.StartDateAfterEndDateException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.day.DayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayDayExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayFromTimeNotInformedException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.Holiday;
import br.com.sawcunha.dayoffmarker.mapper.HolidayMapper;
import br.com.sawcunha.dayoffmarker.repository.HolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.DayService;
import br.com.sawcunha.dayoffmarker.specification.service.HolidayService;
import br.com.sawcunha.dayoffmarker.specification.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HolidayServiceBean implements HolidayService {
    
    private final HolidayRepository holidayRepository;
    private final CountryService countryService;
    private final HolidayMapper holidayMapper;
    private final Validator<Long, HolidayRequestDTO> holidayValidator;
    private final DayService dayService;

	@Transactional(readOnly = true)
	@Override
	public DayOffMarkerResponse<List<HolidayResponseDTO>> findAll(
			final LocalDate startDate,
			final LocalDate endDate,
			final String nameCountry,
			final int page,
			final int sizePerPage,
			final Sort.Direction direction,
			final eOrderHoliday orderHoliday
	) throws Exception {

		Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderHoliday);

		Country country = countryService.findCountryByNameOrDefault(nameCountry);

		Page<Holiday> holidays;
		if(Objects.nonNull(startDate) && Objects.nonNull(endDate)) {

			if (startDate.isAfter(endDate)) {
				throw new StartDateAfterEndDateException();
			}

			holidays = holidayRepository.findAllByCountryAndStartDateAndEndDate(
					country,
					startDate,
					endDate,
					pageable
			);
		} else {
			holidays = holidayRepository.findAllByCountry(country, pageable);
		}

		return DayOffMarkerResponse.<List<HolidayResponseDTO>>builder()
				.data(holidayMapper.toDTOs(holidays.getContent()))
				.paginated(
						PaginationUtils.createPaginated(
								holidays.getTotalPages(),
								holidays.getTotalElements(),
								sizePerPage
						)
				)
				.build();
	}

	@Transactional(readOnly = true)
    @Override
    public DayOffMarkerResponse<HolidayResponseDTO> findById(final Long holidayID) throws Exception {
        Holiday holiday = holidayRepository.findById(holidayID).orElseThrow(HolidayNotExistException::new);
        return DayOffMarkerResponse.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }

    @Transactional(rollbackFor = {
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public DayOffMarkerResponse<HolidayResponseDTO> save(final HolidayRequestDTO holidayRequestDTO) throws Exception {
        holidayValidator.validator(holidayRequestDTO);

        Day day = dayService.findDayByID(holidayRequestDTO.getDayId());

        Holiday holiday = Holiday.builder()
                .name(holidayRequestDTO.getName())
                .description(holidayRequestDTO.getDescription())
                .holidayType(holidayRequestDTO.getHolidayType())
                .fromTime(holidayRequestDTO.getFromTime())
                .day(day)
				.optional(holidayRequestDTO.isOptional())
				.automaticUpdate(false)
                .build();

        holiday = holidayRepository.save(holiday);

        dayService.setDayHoliday(holidayRequestDTO.getDayId(), true);

        return DayOffMarkerResponse.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }

    @Transactional(rollbackFor = {
            HolidayNotExistException.class,
            DayNotExistException.class,
            HolidayDayExistException.class,
            HolidayFromTimeNotInformedException.class
    })
    @Override
    public DayOffMarkerResponse<HolidayResponseDTO> update(
            final Long holidayID,
            final HolidayRequestDTO holidayRequestDTO
    ) throws Exception {
        holidayValidator.validator(holidayID, holidayRequestDTO);

        Holiday holiday = holidayRepository.getById(holidayID);
        if(!holiday.getDay().getId().equals(holidayRequestDTO.getDayId())) {
            dayService.setDayHoliday(holiday.getDay().getId(), false);
            Day day = dayService.findDayByID(holidayRequestDTO.getDayId());
            holiday.setDay(day);
        }

        holiday.setName(holidayRequestDTO.getName());
        holiday.setDescription(holidayRequestDTO.getDescription());
        holiday.setHolidayType(holidayRequestDTO.getHolidayType());
        holiday.setFromTime(holidayRequestDTO.getFromTime());
		holiday.setAutomaticUpdate(false);
		holiday.setOptional(holidayRequestDTO.isOptional());
        holiday = holidayRepository.save(holiday);

        dayService.setDayHoliday(holidayRequestDTO.getDayId(), true);

        return DayOffMarkerResponse.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }

	@Transactional(rollbackFor = {
			DayNotExistException.class,
			HolidayDayExistException.class,
			HolidayFromTimeNotInformedException.class
	})
	@Override
	public void saveHoliday(final HolidayRequestDTO holidayRequestDTO) throws Exception {
		holidayValidator.validator(holidayRequestDTO);

		Day day = dayService.findDayByID(holidayRequestDTO.getDayId());

		Holiday holiday = Holiday.builder()
				.name(holidayRequestDTO.getName())
				.description(holidayRequestDTO.getDescription())
				.holidayType(holidayRequestDTO.getHolidayType())
				.fromTime(holidayRequestDTO.getFromTime())
				.day(day)
				.build();

		holidayRepository.save(holiday);

		dayService.setDayHoliday(holidayRequestDTO.getDayId(), true);
	}
}
