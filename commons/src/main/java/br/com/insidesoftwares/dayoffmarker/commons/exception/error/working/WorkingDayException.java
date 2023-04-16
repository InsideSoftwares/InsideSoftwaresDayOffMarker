package br.com.insidesoftwares.dayoffmarker.commons.exception.error.working;

import br.com.insidesoftwares.dayoffmarker.commons.exception.enumeration.ExceptionCodeError;
import br.com.insidesoftwares.execption.error.InsideSoftwaresException;

public class WorkingDayException extends InsideSoftwaresException {
	public WorkingDayException() {
		super(ExceptionCodeError.WORKING_DAY_NOT_OBTAINED);
	}
}
