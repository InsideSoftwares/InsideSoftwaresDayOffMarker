package br.com.insidesoftwares.dayoffmarker.service.country;

import br.com.insidesoftwares.commons.annotation.InsideAudit;
import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.country.CountryRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryAcronymExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryCodeExistExpetion;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.country.CountryNameExistExpetion;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.country.CountryService;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CountryServiceBean implements CountryService {

    private final CountryRepository countryRepository;
    private final Validator<UUID, CountryRequestDTO> countryValidator;

    @InsideAudit
    @Transactional(rollbackFor = {CountryNameExistExpetion.class, CountryCodeExistExpetion.class, CountryAcronymExistExpetion.class})
    @Override
    public InsideSoftwaresResponseDTO<UUID> save(final @Valid CountryRequestDTO countryRequestDTO) {
        countryValidator.validator(countryRequestDTO);

        Country country = Country.builder()
                .name(countryRequestDTO.name())
                .code(countryRequestDTO.code())
                .acronym(countryRequestDTO.acronym())
                .build();

        country = countryRepository.save(country);

        return InsideSoftwaresResponseDTO.<UUID>builder().data(country.getId()).build();
    }

    @InsideAudit
    @Transactional(rollbackFor = {CountryNameExistExpetion.class, CountryCodeExistExpetion.class, CountryAcronymExistExpetion.class})
    @Override
    public void update(
            final UUID countryID,
            final @Valid CountryRequestDTO countryRequestDTO
    ) {
        countryValidator.validator(countryID, countryRequestDTO);

        Country country = countryRepository.getReferenceById(countryID);
        country.setCode(countryRequestDTO.code());
        country.setAcronym(countryRequestDTO.acronym());
        country.setName(countryRequestDTO.name());

        countryRepository.save(country);
    }

}
