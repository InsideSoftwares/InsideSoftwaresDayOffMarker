package br.com.insidesoftwares.dayoffmarker.job;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getDay;
import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getDayOfWeek;
import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getDayOfYear;
import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getMonth;
import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getTagsID;
import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getYear;

public abstract class RequestJobBean {

    @Autowired
    protected RequestService requestService;

    private String getParameter(
            final Set<RequestParameter> requestParameters,
            TypeParameter typeParameter
    ) throws ParameterNotExistException {
        Optional<RequestParameter> requestParameterOptional =
                requestParameters.stream().filter(requestParameter ->
                        requestParameter.getTypeParameter().equals(typeParameter)
                ).findAny();
        return requestParameterOptional.orElseThrow(ParameterNotExistException::new).getValue();
    }

    public Integer getStartYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.START_YEAR);
        return Integer.parseInt(year);
    }

    public Integer getEndYear(final Set<RequestParameter> requestParameters) throws ParameterNotExistException {
        String year = getParameter(requestParameters, TypeParameter.END_YEAR);
        return Integer.parseInt(year);
    }

    public TagLinkUnlinkRequestDTO createTagLinkRequestDTO(final Set<RequestParameter> requestParameters) {
        Set<UUID> tagsID = getTagsID(requestParameters);
        Integer day = getDay(requestParameters);
        Integer month = getMonth(requestParameters);
        Integer year = getYear(requestParameters);
        Integer dayOfYear = getDayOfYear(requestParameters);
        DayOfWeek dayOfWeek = getDayOfWeek(requestParameters);

        return TagLinkUnlinkRequestDTO.builder()
                .tagsID(tagsID)
                .day(day)
                .month(month)
                .year(year)
                .dayOfYear(dayOfYear)
                .dayOfWeek(dayOfWeek)
                .build();

    }

    public void startRequest(List<Request> requests) {
        requests.forEach(this::startRequest);

        requestService.saveAllAndFlush(requests);
    }

    public void finalizeRequest(List<Request> requests) {
        requests.forEach(this::finalizeRequest);

        requestService.saveAllAndFlush(requests);
    }

    public void startRequest(Request request) {
        request.setStatusRequest(StatusRequest.RUNNING);
        request.setStartDate(LocalDateTime.now());
    }

    public void finalizeRequest(Request request) {
        request.setStatusRequest(StatusRequest.FINALIZED);
        request.setEndDate(LocalDateTime.now());
    }
}
