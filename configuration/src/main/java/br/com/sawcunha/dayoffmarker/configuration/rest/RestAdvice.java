package br.com.sawcunha.dayoffmarker.configuration.rest;

import br.com.sawcunha.dayoffmarker.commons.dto.DayOffMarkerResponse;
import br.com.sawcunha.dayoffmarker.commons.logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Objects;

@RestControllerAdvice
public class RestAdvice implements ResponseBodyAdvice<DayOffMarkerResponse<Object>> {

    @Autowired
    private LogService<RestAdvice> adviceLogService;

	private static final String START_TIME_HEADER = "StartTime";

    @PostConstruct
    public void init(){
        adviceLogService.init(RestAdvice.class);
        adviceLogService.logInfor("Init RestAdvice");
    }

	@Override
	public DayOffMarkerResponse<Object> beforeBodyWrite(DayOffMarkerResponse<Object> body, MethodParameter returnType,
														MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
														ServerHttpRequest request, ServerHttpResponse response) {

		if(Objects.nonNull(body)) {
			long startTime = getStartTime(response);
			long duration = System.currentTimeMillis() - startTime;
			Duration diff = Duration.ofMillis(duration);
			body.setDuration(String.format("%02d:%02d:%02d.%03d",
					diff.toHours(),
					diff.toMinutesPart(),
					diff.toSecondsPart(),
					diff.toMillis()));
		}

		return body;
	}

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getContainingClass().getPackage().getName().contains("br.com.sawcunha.dayoffmarker.controller");
    }

    private Long getStartTime(ServerHttpResponse response){
        if(response.getHeaders() != null && response.getHeaders().containsKey(START_TIME_HEADER) && response.getHeaders().get(START_TIME_HEADER) != null){

            return Long.parseLong(response.getHeaders().get(START_TIME_HEADER).toString().replaceAll("[^\\d]*",""));
        }
        return LocalTime.now().getLong(ChronoField.MILLI_OF_DAY);
    }

}
