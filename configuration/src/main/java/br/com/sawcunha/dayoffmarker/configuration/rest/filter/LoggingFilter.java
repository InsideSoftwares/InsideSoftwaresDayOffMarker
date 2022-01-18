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
@Order(2)
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		log.info(
				"Logging Request  {} : {}. Time: {}",
				req.getMethod(),
				req.getRequestURI(),
				DateUtils.returnDateCurrent()
		);

		chain.doFilter(request, response);

		log.info(
				"Logging Response :{}. Time: {}",
				res.getContentType(), DateUtils.returnDateCurrent());
	}

	// other methods
}
