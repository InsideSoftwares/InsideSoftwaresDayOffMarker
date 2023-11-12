package br.com.insidesoftwares.dayoffmarker.domain.repository.holiday;

import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.QFixedHoliday;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public interface FixedHolidayRepository extends JpaRepository<FixedHoliday, UUID>, QuerydslPredicateExecutor<FixedHoliday> {

    default boolean existsByDayAndMonth(final int day, final int month) {
        return existsByDayAndMonthAndNotId(day, month, null);
    }

    default boolean existsByDayAndMonthAndNotId(final int day, final int month, UUID fixedHolidayId) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QFixedHoliday qFixedHoliday = QFixedHoliday.fixedHoliday;

        booleanBuilder.and(qFixedHoliday.day.eq(day));
        booleanBuilder.and(qFixedHoliday.month.eq(month));

        if (Objects.nonNull(fixedHolidayId)) {
            booleanBuilder.and(qFixedHoliday.id.eq(fixedHolidayId));
        }

        return exists(booleanBuilder.getValue());
    }

    default Iterable<FixedHoliday> findAllByFixedHolidayIdAndIsEnable(final UUID fixedHolidayId, final boolean isEnable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QFixedHoliday qFixedHoliday = QFixedHoliday.fixedHoliday;

        booleanBuilder.and(qFixedHoliday.enable.eq(isEnable));

        if(Objects.nonNull(fixedHolidayId)) {
            booleanBuilder.and(qFixedHoliday.id.eq(fixedHolidayId));
        }

        return findAll(booleanBuilder.getValue());
    }
}
