package br.com.sawcunha.dayoffmarker.commons.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DateUtils {


    private final static int YEAR_BASE = 2022;
    private final static int YEAR_LEAP_BASE = 2024;
    private final static int MONTH_31_DAY = 31;
    private final static int MONTH_30_DAY = 30;
    private final static int FEBRUARY = 2;
    private final static int FEBRUARY_DAY = 29;
    private final static List<Integer> MONTHS_31_DAYS = List.of(1,3,5,7,8,10,12);
    private final static List<Integer> MONTHS_30_DAYS = List.of(4,6,9,11);

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

}
