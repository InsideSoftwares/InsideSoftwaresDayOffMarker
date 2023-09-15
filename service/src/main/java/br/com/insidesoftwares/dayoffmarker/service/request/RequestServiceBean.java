package br.com.insidesoftwares.dayoffmarker.service.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.repository.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class RequestServiceBean implements RequestService {

	private final RequestRepository requestRepository;

	@Transactional
	@Override
	public void saveRequest(final Request request) {
		requestRepository.save(request);
	}

	@Transactional
	@Override
	public void saveAndFlushRequest(final Request request) {
		requestRepository.saveAndFlush(request);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Request> findAllRequestByTypeAndStatus(final TypeRequest typeRequest, final StatusRequest statusRequest) {
		return requestRepository.findAllByStatusRequest(statusRequest, typeRequest);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean existRequestByTypeAndStatusRequest(TypeRequest typeRequest, StatusRequest statusRequest) {
		return requestRepository.existRequestByTypeRequestAndStatusRequest(typeRequest, statusRequest);
	}

}
