package br.com.insidesoftwares.dayoffmarker.service.holiday;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.holiday.FixedHolidayUpdateRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.day.DayMonthInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayDayMonthCountryExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.fixedholiday.FixedHolidayNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.FixedHolidayService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class FixedHolidayServiceBean implements FixedHolidayService {

    private final FixedHolidayRepository fixedHolidayRepository;
    private final Validator<UUID, FixedHolidayRequestDTO> fixedHolidayValidator;
    private final Validator<UUID, FixedHolidayUpdateRequestDTO> fixedHolidayUpdateValidator;

    @InsideAudit
    @Transactional(rollbackFor = {
            CountryNotExistException.class,
            DayMonthInvalidException.class,
            FixedHolidayDayMonthCountryExistException.class
    })
    @Override
    public InsideSoftwaresResponseDTO<UUID> save(final @Valid FixedHolidayRequestDTO fixedHolidayRequestDTO) {
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

        fixedHoliday = fixedHolidayRepository.save(fixedHoliday);

        return InsideSoftwaresResponseDTO.<UUID>builder().data(fixedHoliday.getId()).build();
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
            final UUID fixedHolidayID,
            final @Valid FixedHolidayUpdateRequestDTO fixedHolidayRequestDTO
    ) {
        fixedHolidayUpdateValidator.validator(fixedHolidayID, fixedHolidayRequestDTO);

        FixedHoliday fixedHoliday = fixedHolidayRepository.getReferenceById(fixedHolidayID);

        fixedHoliday.setName(fixedHolidayRequestDTO.name());
        fixedHoliday.setDescription(fixedHolidayRequestDTO.description());
        fixedHoliday.setOptional(fixedHolidayRequestDTO.isOptional());
        fixedHoliday.setFromTime(fixedHolidayRequestDTO.fromTime());
        fixedHoliday.setEnable(Objects.nonNull(fixedHolidayRequestDTO.isEnable()) ? fixedHolidayRequestDTO.isEnable() : fixedHoliday.isEnable());

        fixedHolidayRepository.save(fixedHoliday);
    }
}
