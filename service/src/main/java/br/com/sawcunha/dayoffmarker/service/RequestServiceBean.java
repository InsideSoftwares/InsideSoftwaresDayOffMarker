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
public class RequestServiceBean implements RequestService {

	private final RequestRepository requestRepository;
	private final RequestMapper requestMapper;

	@Override
	public void saveRequest(Request request) {
		requestRepository.saveAndFlush(request);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Request> findAllRequestForBatch(final eTypeRequest typeRequest, final eStatusRequest statusRequest) {
		return requestRepository.findAllByStatusRequest(statusRequest, eTypeRequest.CREATE_DATE);
	}
	@Transactional(readOnly = true)
	@Override
	public List<Request> findAllRequestForBatch(final Long jobId, final eStatusRequest statusRequest) {
		return requestRepository.findAllByJobIdAndStatusRequest(jobId, statusRequest, eTypeRequest.CREATE_DATE);
	}

	@Override
	public List<RequestDTO> findAllRequestDTOForBatch(eStatusRequest statusRequest) {
		return requestMapper.toDTOs(
				requestRepository.findAllByStatusRequest(statusRequest, eTypeRequest.CREATE_DATE)
		);
	}

	@Override
	public List<RequestDTO> findAllRequestDTOForBatch(final Long jobId, eStatusRequest statusRequest){
		return requestMapper.toDTOs(
				requestRepository.findAllByJobIdAndStatusRequest(jobId, statusRequest, eTypeRequest.CREATE_DATE)
		);
	}

}
