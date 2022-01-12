package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.entity.Day;

public interface DayService {

    Day findDayByID(final Long dayID) throws Exception;

    void setDayHoliday(final Long dayID,final boolean isHoliday) throws Exception;
}
