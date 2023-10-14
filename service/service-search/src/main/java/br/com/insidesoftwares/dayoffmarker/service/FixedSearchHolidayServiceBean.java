package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.commons.utils.PaginationUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderFixedHoliday;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.mapper.FixedHolidayMapper;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.search.FixedHolidaySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class FixedSearchHolidayServiceBean implements FixedHolidaySearchService {

    private final FixedHolidayRepository fixedHolidayRepository;
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
    @Override
    public List<FixedHoliday> findAllByEnable(final boolean isEnable) {
        return fixedHolidayRepository.findAllByIsEnable(isEnable);
    }

    @InsideAudit
    @Override
    public FixedHoliday findFixedHolidayById(Long fixedHolidayID) throws FixedHolidayNotExistException {
        return fixedHolidayRepository.findById(fixedHolidayID).orElseThrow(FixedHolidayNotExistException::new);
    }
}
