package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.entity.Day;

public interface DayService {

    Day findDayByID(Long dayID) throws Exception;

}
