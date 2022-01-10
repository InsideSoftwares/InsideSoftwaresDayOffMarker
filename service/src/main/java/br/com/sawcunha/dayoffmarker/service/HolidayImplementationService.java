package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.response.holiday.HolidayDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Holiday;
import br.com.sawcunha.dayoffmarker.repository.HolidayRepository;
import br.com.sawcunha.dayoffmarker.specification.service.CountryService;
import br.com.sawcunha.dayoffmarker.specification.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayImplementationService implements HolidayService {
    
    private final HolidayRepository holidayRepository;
    private final CountryService countryService;
    
    @Override
    public DayOffMarkerResponse<List<HolidayDTO>> findAll(String nameCountry, int page, int sizePerPage, Sort.Direction direction, eOrderHoliday orderHoliday) throws Exception {
        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderHoliday);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<Holiday> states = holidayRepository.findAllByCountry(country, pageable);

        return DayOffMarkerResponse.<List<HolidayDTO>>builder()
                .data(createHolidayDTOs(states))
                .paginated(
                        PaginationUtils.createPaginated(
                                states.getTotalPages(),
                                states.getTotalElements(),
                                sizePerPage
                        )
                )
                .build();
    }

    @Override
    public DayOffMarkerResponse<HolidayDTO> findById(Long holidayID) throws Exception {
        Holiday holiday = holidayRepository.findById(holidayID).orElseThrow(HolidayNotExistException::new);
        return DayOffMarkerResponse.<HolidayDTO>builder()
                .data(createHolidayDTO(holiday))
                .build();
    }


    private List<HolidayDTO> createHolidayDTOs(final Page<Holiday> holidays){
        return holidays.map(this::createHolidayDTO)
                .stream().toList();

    }

    private HolidayDTO createHolidayDTO(final Holiday holiday){
        return HolidayDTO.builder()
                .id(holiday.getId())
                .name(holiday.getName())
                .description(holiday.getDescription())
                .holidayType(holiday.getHolidayType())
                .fromTime(holiday.getFromTime())
                .day(holiday.getDay().getDate())
                .build();
    }
}
