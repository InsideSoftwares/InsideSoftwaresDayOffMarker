package br.com.insidesoftwares.dayoffmarker.job;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeParameter;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.RequestParameter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class RequestParametersUtils {

    private static final String PIPE_REGEX = "\\|";

    private static RequestParameter getParameter(
            final Set<RequestParameter> requestParameters,
            final TypeParameter typeParameter) {
        Optional<RequestParameter> optionalRequestParameter =
                requestParameters.stream().filter(requestParameter ->
                        requestParameter.getTypeParameter().equals(typeParameter)
                ).findAny();
        return optionalRequestParameter.orElse(null);
    }

    public static Integer getDay(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.DAY);
        return nonNull(requestParameter) ? parseInt(requestParameter.getValue()) : null;
    }

    public static Integer getYear(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.YEAR);
        return nonNull(requestParameter) ? parseInt(requestParameter.getValue()) : null;
    }

    public static DayOfWeek getDayOfWeek(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.DAY_OF_WEEK);
        return nonNull(requestParameter) ? DayOfWeek.valueOf(requestParameter.getValue()) : null;
    }

    public static Integer getDayOfYear(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.DAY_OF_YEAR);
        return nonNull(requestParameter) ? parseInt(requestParameter.getValue()) : null;
    }

    public static Integer getMonth(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.MONTH);
        return nonNull(requestParameter) ? parseInt(requestParameter.getValue()) : null;
    }

    public static UUID getFixedHolidayID(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.FIXED_HOLIDAY_ID);
        return nonNull(requestParameter) ? UUID.fromString(requestParameter.getValue()) : null;
    }

    public static Long getTagID(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.TAG_ID);
        return nonNull(requestParameter) ? Long.parseLong(requestParameter.getValue()) : null;
    }

    public static Set<UUID> getTagsID(Set<RequestParameter> requestParameters) {
        RequestParameter requestParameter = getParameter(requestParameters, TypeParameter.TAG_ID);
        if (isNull(requestParameter)) return null;
        return Arrays.stream(requestParameter.getValue().split(PIPE_REGEX)).map(UUID::fromString).collect(Collectors.toSet());
    }
}
