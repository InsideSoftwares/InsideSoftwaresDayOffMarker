package br.com.sawcunha.dayoffmarker.configuration.rest.filter;

import br.com.sawcunha.dayoffmarker.commons.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class EditHeaderFilter implements Filter {

	private static final String START_TIME_HEADER = "StartTime";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

		log.info(
				"Edit Header Request  {} : {}. Time: {}",
				req.getMethod(),
				req.getRequestURI(),
				DateUtils.returnDateCurrent()
		);

        res.addHeader(START_TIME_HEADER, String.valueOf(startTime));

        chain.doFilter(req, res);

		log.info(
				"Edit Header Response :{}. Time: {}",
				res.getContentType(), DateUtils.returnDateCurrent());
    }
}
