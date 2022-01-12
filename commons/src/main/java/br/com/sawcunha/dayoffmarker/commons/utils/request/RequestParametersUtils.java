package br.com.sawcunha.dayoffmarker.commons.utils.request;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestParameterDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeParameter;

import java.util.List;

public class RequestParametersUtils {

    public RequestParametersUtils() {
        //Empty
    }

    private String getParameter(List<RequestParameterDTO> requestParameterDTOS){
        return "";
    }

    private Integer getYear(List<RequestParameterDTO> requestParameterDTOS){
        String year = requestParameterDTOS.stream().filter(requestParameterDTO ->
                requestParameterDTO.getTypeParameter().equals(eTypeParameter.YEAR)
        ).findAny().get().getValue();
        return Integer.parseInt(year);
    }

    private Integer getMonth(List<RequestParameterDTO> requestParameterDTOS){
        String month = requestParameterDTOS.stream().filter(requestParameterDTO ->
                requestParameterDTO.getTypeParameter().equals(eTypeParameter.MONTH)
        ).findAny().get().getValue();
        return Integer.parseInt(month);
    }

    private Long getCountry(List<RequestParameterDTO> requestParameterDTOS){
        String country = requestParameterDTOS.stream().filter(requestParameterDTO ->
                requestParameterDTO.getTypeParameter().equals(eTypeParameter.COUNTRY)
        ).findAny().get().getValue();
        return Long.parseLong(country);
    }
}
