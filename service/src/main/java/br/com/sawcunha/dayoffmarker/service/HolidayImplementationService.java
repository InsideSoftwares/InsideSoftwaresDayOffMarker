package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.HolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayImplementationService implements HolidayService {
    
    private final HolidayRepository holidayRepository;
    private final CountryService countryService;
    private final HolidayMapper holidayMapper;
    private final Validator<Long, HolidayRequestDTO> holidayValidator;
    private final DayService dayService;
    
    @Override
    public DayOffMarkerResponse<List<HolidayResponseDTO>> findAll(String nameCountry, int page, int sizePerPage, Sort.Direction direction, eOrderHoliday orderHoliday) throws Exception {
        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderHoliday);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<Holiday> holidays = holidayRepository.findAllByCountry(country, pageable);

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

    @Override
    public DayOffMarkerResponse<HolidayResponseDTO> findById(Long holidayID) throws Exception {
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
    public DayOffMarkerResponse<HolidayResponseDTO> save(HolidayRequestDTO holidayRequestDTO) throws Exception {
        holidayValidator.validator(holidayRequestDTO);

        Day day = dayService.findDayByID(holidayRequestDTO.getDayId());

        Holiday holiday = Holiday.builder()
                .name(holidayRequestDTO.getName())
                .description(holidayRequestDTO.getDescription())
                .holidayType(holidayRequestDTO.getHolidayType())
                .fromTime(holidayRequestDTO.getFromTime())
                .day(day)
                .build();

        holiday = holidayRepository.save(holiday);

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
    public DayOffMarkerResponse<HolidayResponseDTO> update(Long holidayID, HolidayRequestDTO holidayRequestDTO) throws Exception {
        holidayValidator.validator(holidayID, holidayRequestDTO);

        Holiday holiday = holidayRepository.getById(holidayID);
        if(!holiday.getDay().getId().equals(holidayRequestDTO.getDayId())) {
            Day day = dayService.findDayByID(holidayRequestDTO.getDayId());
            holiday.setDay(day);
        }

        holiday.setName(holidayRequestDTO.getName());
        holiday.setDescription(holidayRequestDTO.getDescription());
        holiday.setHolidayType(holidayRequestDTO.getHolidayType());
        holiday.setFromTime(holidayRequestDTO.getFromTime());

        holiday = holidayRepository.save(holiday);

        return DayOffMarkerResponse.<HolidayResponseDTO>builder()
                .data(holidayMapper.toDTO(holiday))
                .build();
    }
}
