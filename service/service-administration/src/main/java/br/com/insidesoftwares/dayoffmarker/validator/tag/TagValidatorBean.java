package br.com.insidesoftwares.dayoffmarker.validator.tag;

import br.com.insidesoftwares.commons.utils.DateUtils;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagCodeExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkDateInvalidException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkOneParameterNotNullException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagLinkParameterNotResultException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.tag.TagNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.tag.TagRepository;
import br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification;
import br.com.insidesoftwares.dayoffmarker.specification.validator.Validator;
import br.com.insidesoftwares.dayoffmarker.specification.validator.ValidatorLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class TagValidatorBean implements Validator<UUID, TagRequestDTO>, ValidatorLink<TagLinkUnlinkRequestDTO> {

    private final TagRepository tagRepository;
    private final DayRepository dayRepository;

    @Override
    public void validator(final TagRequestDTO tagRequestDTO) {
        if (tagRepository.existsByCode(tagRequestDTO.code())) throw new TagCodeExistException();
    }

    @Override
    public void validator(final UUID tagID, final TagRequestDTO tagRequestDTO) {
        if (!tagRepository.existsById(tagID)) throw new TagNotExistException();
        if (tagRepository.existsByCodeAndNotId(tagRequestDTO.code(), tagID)) throw new TagCodeExistException();
    }

    @Override
    public void validator(final UUID tagID) {
        if (!tagRepository.existsById(tagID)) throw new TagNotExistException();
    }

    @Override
    public void validateLinkUnlink(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO) {

        if (
                Objects.isNull(tagLinkUnlinkRequestDTO.dayOfWeek()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.dayOfYear()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.year())
        ) throw new TagLinkOneParameterNotNullException();

        if (
                Objects.nonNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.year()) &&
                        !DateUtils.isDateValid(tagLinkUnlinkRequestDTO.day(), tagLinkUnlinkRequestDTO.month(), tagLinkUnlinkRequestDTO.year())
        ) {
            throw new TagLinkDateInvalidException();
        }

        if (
                Objects.nonNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        !DateUtils.isDateValid(tagLinkUnlinkRequestDTO.day(), tagLinkUnlinkRequestDTO.month())
        ) {
            throw new TagLinkDateInvalidException();
        }

        Specification<Day> daySpecification = DaySpecification.exitsDayByTagLinkRequestDTO(tagLinkUnlinkRequestDTO);
        boolean exitsDayByTagLinkRequestDTO = dayRepository.exists(daySpecification);

        if (!exitsDayByTagLinkRequestDTO) {
            throw new TagLinkParameterNotResultException();
        }

        tagLinkUnlinkRequestDTO.tagsID().forEach(tagID -> {
            if (!tagRepository.existsById(tagID)) throw new TagLinkNotExistException(tagID);
        });

    }
}
