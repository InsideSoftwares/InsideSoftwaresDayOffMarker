package br.com.sawcunha.dayoffmarker.commons.utils.request;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestParameterDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class RequestParametersUtils {

	private static RequestParameterDTO getParameter(
			final Set<RequestParameterDTO> requestParameterDTOS,
			final eTypeParameter typeParameter){
		Optional<RequestParameterDTO> optionalRequestParameterDTO =
				requestParameterDTOS.stream().filter(requestParameterDTO ->
					requestParameterDTO.getTypeParameter().equals(typeParameter)
			).findAny();
        return optionalRequestParameterDTO.orElse(null);
    }

    public static Integer getYear(Set<RequestParameterDTO> requestParameterDTOS){
		RequestParameterDTO requestParameterDTO = getParameter(requestParameterDTOS, eTypeParameter.YEAR);
        return Objects.nonNull(requestParameterDTO) ? Integer.parseInt(requestParameterDTO.getValue()) : null;
    }

	public static Integer getMonth(Set<RequestParameterDTO> requestParameterDTOS){
		RequestParameterDTO requestParameterDTO = getParameter(requestParameterDTOS, eTypeParameter.MONTH);
		return Objects.nonNull(requestParameterDTO) ? Integer.parseInt(requestParameterDTO.getValue()) : null;
    }

	public static Long getCountry(Set<RequestParameterDTO> requestParameterDTOS){
		RequestParameterDTO requestParameterDTO = getParameter(requestParameterDTOS, eTypeParameter.COUNTRY);
		return Objects.nonNull(requestParameterDTO) ? Long.parseLong(requestParameterDTO.getValue()) : null;
    }

	public static Long getFixedHolidayID(Set<RequestParameterDTO> requestParameterDTOS){
		RequestParameterDTO requestParameterDTO = getParameter(requestParameterDTOS, eTypeParameter.FIXED_HOLIDAY_ID);
		return Objects.nonNull(requestParameterDTO) ? Long.parseLong(requestParameterDTO.getValue()) : null;
	}
}
