package br.com.sawcunha.dayoffmarker.commons.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateUtils {

	private static final int MONTH_31_DAY = 31;
	private static final int MONTH_30_DAY = 30;
	private static final int FEBRUARY = 2;
	private static final int FEBRUARY_DAY = 29;
	private static final List<Integer> MONTHS_31_DAYS = List.of(1,3,5,7,8,10,12);
	private static final List<Integer> MONTHS_30_DAYS = List.of(4,6,9,11);
	private static final DateTimeFormatter FORMATER_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isWeenkend(LocalDate ld) {
        DayOfWeek d = ld.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }

    public static LocalDate plusYear(LocalDate date, int year){
        return date.plusYears(year);
    }

    public static LocalDate minusYear(LocalDate date, int year){
        return date.minusYears(year);
    }

    public static String returnYearPlus(LocalDate date, int year){
        return String.format("%s",plusYear(date, year).getYear());
    }

    public static String returnYearMinus(LocalDate date, int year){
        return String.format("%s",minusYear(date, year).getYear());
    }

    public static String returnYearPlus(LocalDate date, String year){
        return returnYearPlus(date,Integer.parseInt(year));
    }

    public static String returnYearMinus(LocalDate date, String year){
        return returnYearMinus(date,Integer.parseInt(year));
    }

    public static boolean isDateValid(Integer day, Integer month){
        if(day.equals(MONTH_31_DAY) && MONTHS_31_DAYS.contains(month)) return true;
        if(day.equals(MONTH_30_DAY) && MONTHS_31_DAYS.contains(month)) return true;
        if(day.equals(MONTH_30_DAY) && MONTHS_30_DAYS.contains(month)) return true;
        return month.equals(FEBRUARY) && day <= FEBRUARY_DAY;
    }

	public static String returnDateCurrent(){
		return LocalDateTime.now().format(FORMATER_TIME);
	}

	public static String returnDate(final LocalDate date){
		return date.format(FORMATER);
	}
}
