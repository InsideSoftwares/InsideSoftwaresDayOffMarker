package br.com.insidesoftwares.dayoffmarker.domain.specification.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationBetween;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationGreaterThanOrEqualTo;
import static br.com.insidesoftwares.commons.utils.SpecificationRepository.specificationLessThanOrEqualTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableDaySpecification extends DaySpecificationBase {

    public static Specification<Day> findAllPageableByStartDateAndEndDate(final LocalDate startDate, final LocalDate endDate) {
        Specification<Day> daySpecification = Specification.where(null);
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            validStartDateAfterEndDate(startDate, endDate);
            daySpecification = daySpecification.and(specificationBetween(DAY_DATE, startDate, endDate));
        }

        if (Objects.nonNull(startDate) && Objects.isNull(endDate)) {
            daySpecification = daySpecification.and(specificationGreaterThanOrEqualTo(DAY_DATE, startDate));
        }

        if (Objects.isNull(startDate) && Objects.nonNull(endDate)) {
            daySpecification = daySpecification.and(specificationLessThanOrEqualTo(DAY_DATE, endDate));
        }

        return daySpecification;
    }
}
