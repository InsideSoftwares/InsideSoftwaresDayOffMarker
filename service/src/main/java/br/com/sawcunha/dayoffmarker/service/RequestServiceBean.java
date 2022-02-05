package br.com.sawcunha.dayoffmarker.service;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.mapper.RequestMapper;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class RequestServiceBean implements RequestService {

	private final RequestRepository requestRepository;
	private final RequestMapper requestMapper;

	@Override
	public void saveRequest(final Request request) {
		requestRepository.save(request);
	}

	@Override
	public void saveAndFlushRequest(final Request request) {
		requestRepository.saveAndFlush(request);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Request> findAllRequestByTypeAndStatus(final eTypeRequest typeRequest, final eStatusRequest statusRequest) {
		return requestRepository.findAllByStatusRequest(statusRequest, typeRequest);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Request> findAllRequestByJobIDAndStatus(final Long jobId, final eStatusRequest statusRequest) {
		return requestRepository.findAllByJobIdAndStatusRequest(jobId, statusRequest);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existRequestByByTypeAndStatusRequest(eTypeRequest typeRequest, eStatusRequest statusRequest) {
		return requestRepository.existRequestByTypeRequestAndStatusRequest(eTypeRequest.CREATE_DATE, eStatusRequest.CREATED);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RequestDTO> findAllRequestDTOForBatch(final eTypeRequest typeRequest, final eStatusRequest statusRequest) {
		return requestMapper.toDTOs(
				requestRepository.findAllByStatusRequest(statusRequest, typeRequest)
		);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RequestDTO> findAllRequestDTOForBatch(final Long jobId, eStatusRequest statusRequest){
		return requestMapper.toDTOs(
				requestRepository.findAllByJobIdAndStatusRequest(jobId, statusRequest)
		);
	}

}
