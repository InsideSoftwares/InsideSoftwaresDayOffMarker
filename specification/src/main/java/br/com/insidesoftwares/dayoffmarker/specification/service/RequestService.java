package br.com.insidesoftwares.dayoffmarker.specification.service;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RequestService {

	void saveRequest(final Request request);
	void saveAndFlushRequest(Request request);

	List<Request> findAllRequestByTypeAndStatus(final TypeRequest typeRequest, final StatusRequest statusRequest);

	boolean existRequestByTypeAndStatusRequest(final TypeRequest typeRequest, final StatusRequest statusRequest);

}
