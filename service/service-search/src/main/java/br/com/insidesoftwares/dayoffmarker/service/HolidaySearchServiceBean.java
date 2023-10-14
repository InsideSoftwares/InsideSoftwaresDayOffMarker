package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.holiday.HolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.holiday.HolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.Holiday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.HolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.HolidayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.HolidaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.search.HolidaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class HolidaySearchServiceBean implements HolidaySearchService {

    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;

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
    @Override
    public Holiday findHolidayById(Long holidayID) {
        return holidayRepository.findById(holidayID).orElseThrow(HolidayNotExistException::new);
    }
}
