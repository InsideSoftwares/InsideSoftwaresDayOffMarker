package br.com.insidesoftwares.dayoffmarker.service.request;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.repository.request.RequestRepository;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
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
    public void saveAllAndFlush(final List<Request> requests) {
        requestRepository.saveAllAndFlush(requests);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Request> findAllRequestByTypeAndStatus(final TypeRequest typeRequest, final StatusRequest statusRequest) {
        return requestRepository.findAllByStatusRequestAndTypeRequest(statusRequest, typeRequest);
    }

}
