package br.com.insidesoftwares.dayoffmarker.domain.specification.tag;

import br.com.insidesoftwares.commons.enums.SpecificationFunction;
import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationEqual;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagSpecification {

    private static final String DAY_OF_WEEK = "dayOfWeek";
    private static final String DAY_OF_YEAR = "dayOfYear";
    private static final String DAY_DATE = "date";

    public static Specification<Day> findAllDayByTagLinkRequestDTO(final TagLinkUnlinkRequestDTO tagLinkUnlinkRequestDTO) {
        Specification<Day> daySpecification = Specification.where(null);

        if (Objects.nonNull(tagLinkUnlinkRequestDTO.dayOfWeek())) {
            daySpecification = daySpecification.and(specificationEqual(DAY_OF_WEEK, tagLinkUnlinkRequestDTO.dayOfWeek()));
        }

        if (Objects.nonNull(tagLinkUnlinkRequestDTO.dayOfYear())) {
            daySpecification = daySpecification.and(specificationEqual(DAY_OF_YEAR, tagLinkUnlinkRequestDTO.dayOfYear()));
        }

        if (
                Objects.nonNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.year())
        ) {
            LocalDate date = LocalDate.of(tagLinkUnlinkRequestDTO.year(), tagLinkUnlinkRequestDTO.month(), tagLinkUnlinkRequestDTO.day());
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, date));
        }

        if (
                Objects.nonNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.year())
        ) {
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_DAY, tagLinkUnlinkRequestDTO.day()));
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_MONTH, tagLinkUnlinkRequestDTO.month()));
        }

        if (
                Objects.nonNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.year())
        ) {
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_DAY, tagLinkUnlinkRequestDTO.day()));
        }

        if (
                Objects.isNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.year())
        ) {
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_MONTH, tagLinkUnlinkRequestDTO.month()));
        }

        if (
                Objects.isNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.isNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.year())
        ) {
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_YEAR, tagLinkUnlinkRequestDTO.year()));
        }

        if (
                Objects.isNull(tagLinkUnlinkRequestDTO.day()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.month()) &&
                        Objects.nonNull(tagLinkUnlinkRequestDTO.year())
        ) {
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_MONTH, tagLinkUnlinkRequestDTO.month()));
            daySpecification = daySpecification.and(specificationEqual(DAY_DATE, SpecificationFunction.DATE_PART_YEAR, tagLinkUnlinkRequestDTO.year()));
        }

        return daySpecification;
    }

}
