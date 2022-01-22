package br.com.sawcunha.dayoffmarker.specification.service;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RequestService {

	void saveRequest(final Request request);
	void saveAndFlushRequest(Request request);

	List<Request> findAllRequestByTypeAndStatus(final eTypeRequest typeRequest, final eStatusRequest statusRequest);
	List<Request> findAllRequestByJobIDAndStatus(final Long jobId, final eStatusRequest statusRequest);

	boolean existRequestByByTypeAndStatusRequest(final eTypeRequest typeRequest, final eStatusRequest statusRequest);

	List<RequestDTO> findAllRequestDTOForBatch(final eTypeRequest typeRequest, final eStatusRequest statusRequest);
	List<RequestDTO> findAllRequestDTOForBatch(final Long jobId, final eStatusRequest statusRequest);


}
