package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.dto.request.FixedHolidayRequestDTO;
import br.com.sawcunha.dayoffmarker.commons.dto.response.fixedholiday.FixedHolidayResponseDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.sort.eOrderFixedHoliday;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayMonthInvalidException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.sawcunha.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.sawcunha.dayoffmarker.commons.utils.PaginationUtils;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.FixedHoliday;
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
public class FixedHolidayImplementationService  implements FixedHolidayService {

    private final CountryService countryService;
    private final FixedHolidayRepository fixedHolidayRepository;
    private final Validator<Long, FixedHolidayRequestDTO> fixedHolidayValidator;

    @Override
    @Transactional(readOnly = true)
    public DayOffMarkerResponse<List<FixedHolidayResponseDTO>> findAll(
            final String nameCountry,
            final int page,
            final int sizePerPage,
            final Sort.Direction direction,
            final eOrderFixedHoliday orderFixedHoliday
    ) throws Exception {

        Pageable pageable = PaginationUtils.createPageable(page, sizePerPage, direction, orderFixedHoliday);

        Country country = countryService.findCountryByNameOrDefault(nameCountry);

        Page<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByCountry(country,pageable);
        return DayOffMarkerResponse.<List<FixedHolidayResponseDTO>>builder()
                .data(createFixedHolidayDTOs(fixedHolidays))
                .paginated(
                        PaginationUtils.createPaginated(
                                fixedHolidays.getTotalPages(),
                                fixedHolidays.getTotalElements(),
                                sizePerPage
                        )
                )
                .build();

    }

    @Transactional(readOnly = true)
    @Override
    public DayOffMarkerResponse<FixedHolidayResponseDTO> findById(final Long fixedHolidayID) throws FixedHolidayNotExistException {
        FixedHoliday fixedHoliday = fixedHolidayRepository
                .findById(fixedHolidayID)
                .orElseThrow(FixedHolidayNotExistException::new);

        return DayOffMarkerResponse.<FixedHolidayResponseDTO>builder()
                .data(createFixedHolidayDTO(fixedHoliday))
                .build();
    }

    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class
    })
    @Override
    public DayOffMarkerResponse<FixedHolidayResponseDTO> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO) throws Exception {
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
                .data(createFixedHolidayDTO(fixedHoliday))
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
            final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO
    ) throws Exception {
        fixedHolidayValidator.validator(fixedHolidayID,fixedHolidayRequestDTO);

        FixedHoliday fixedHoliday = fixedHolidayRepository.getById(fixedHolidayID);

        if(fixedHoliday.getCountry().getId().equals(fixedHolidayRequestDTO.getCountryId())){
            Country country = countryService.findCountryByCountryId(fixedHolidayRequestDTO.getCountryId());
            fixedHoliday.setCountry(country);
        }

        fixedHoliday.setName(fixedHolidayRequestDTO.getName());
        fixedHoliday.setDescription(fixedHolidayRequestDTO.getDescription());
        fixedHoliday.setDay(fixedHolidayRequestDTO.getDay());
        fixedHoliday.setMonth(fixedHolidayRequestDTO.getMonth());
        fixedHoliday.setOptional(fixedHolidayRequestDTO.getIsOptional());
        fixedHoliday.setFromTime(fixedHolidayRequestDTO.getFromTime());

        fixedHoliday = fixedHolidayRepository.save(fixedHoliday);

        return DayOffMarkerResponse.<FixedHolidayResponseDTO>builder()
                .data(createFixedHolidayDTO(fixedHoliday))
                .build();
    }

    private List<FixedHolidayResponseDTO> createFixedHolidayDTOs(final Page<FixedHoliday> fixedHolidays){
        return fixedHolidays.map(this::createFixedHolidayDTO)
                .stream().toList();

    }

    private FixedHolidayResponseDTO createFixedHolidayDTO(final FixedHoliday fixedHoliday){
        return FixedHolidayResponseDTO.builder()
                    .id(fixedHoliday.getId())
                    .day(fixedHoliday.getDay())
                    .month(fixedHoliday.getMonth())
                    .name(fixedHoliday.getName())
                    .description(fixedHoliday.getDescription())
                    .fromTime(fixedHoliday.getFromTime())
                    .isOptional(fixedHoliday.isOptional())
                    .countryId(fixedHoliday.getCountry().getId())
                    .countryName(fixedHoliday.getCountry().getName())
                .build();
    }

}
