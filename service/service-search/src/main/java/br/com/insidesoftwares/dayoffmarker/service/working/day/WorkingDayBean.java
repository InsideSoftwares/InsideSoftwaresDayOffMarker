package br.com.insidesoftwares.dayoffmarker.service.working.day;

import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.repository.day.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.day.DaySpecification.findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates;

abstract class WorkingDayBean {

    private static final int MINUMO = 5;
    private static final int MUTIPLICATOR = 2;

    @Autowired
    private DayRepository dayRepository;

    int getSearchRangeForDays(final int numberOfDays) {
        int numberAbsOfDays = Math.abs(numberOfDays);
        return Math.max(MINUMO, MUTIPLICATOR * numberAbsOfDays);
    }

    Day searchForWorkDayInListOfDays(final LocalDate dateReceived, final List<Day> dates, final int range, final boolean isWorkingDayNext) {
        Optional<Day> dateOptional = findDayByDate(dates, dateReceived);

        return dateOptional.orElseGet(() -> getWorkingDay(dateReceived, dates, 1, range, isWorkingDayNext));
    }

    LocalDate updateDateByRange(final LocalDate data, final boolean future, final int range) {
        int rangeAbs = Math.abs(range);
        return future ? data.plusDays(rangeAbs) : data.minusDays(rangeAbs);
    }

    boolean checkIfSearchFutureDates(final int numberOfDays) {
        return numberOfDays >= 0;
    }

    List<Day> findAllWorkingDays(
            final LocalDate dateStartSearch,
            final LocalDate dateFinalSearch,
            final boolean isSearchForFutureDates
    ) {
        boolean isHoliday = false;
        boolean isWeekend = false;

        Specification<Day> daySpecification = findAllByDateSearchAndHolidayAndWeekendAndSearchFutureDates(
                dateStartSearch, dateFinalSearch, isHoliday, isWeekend, isSearchForFutureDates);

        return dayRepository.findAll(daySpecification);
    }

    private Day getWorkingDay(
            final LocalDate dateReceived,
            final List<Day> dates,
            final int index,
            final int limit,
            final boolean isWorkingDayNext
    ) {
        if (index >= limit) {
            return null;
        }

        LocalDate newDateReceived = updateDateByRange(dateReceived, isWorkingDayNext, 1);

        Optional<Day> dayOptional = findDayByDate(dates, newDateReceived);
        return dayOptional.orElseGet(() -> getWorkingDay(newDateReceived, dates, index + 1, limit, isWorkingDayNext));
    }

    private Optional<Day> findDayByDate(final List<Day> dates, final LocalDate dateReceived) {
        return dates.stream().filter(data -> data.getDate().isEqual(dateReceived)).findFirst();
    }

}
