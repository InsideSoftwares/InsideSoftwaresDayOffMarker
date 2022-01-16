package br.com.sawcunha.dayoffmarker.service.batch;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.enums.eTypeRequest;
import br.com.sawcunha.dayoffmarker.entity.Country;
import br.com.sawcunha.dayoffmarker.entity.Day;
import br.com.sawcunha.dayoffmarker.entity.DayBatch;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.mapper.RequestMapper;
import br.com.sawcunha.dayoffmarker.repository.CountryRepository;
import br.com.sawcunha.dayoffmarker.repository.DayBatchRepository;
import br.com.sawcunha.dayoffmarker.repository.DayRepository;
import br.com.sawcunha.dayoffmarker.repository.RequestRepository;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BatchCreationDayImplementationService implements BatchCreationDayService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final DayBatchRepository dayBatchRepository;
    private final DayRepository dayRepository;
    private final CountryRepository countryRepository;


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

	@Override
	public List<Request> findAllRequestForBatch(eStatusRequest statusRequest) {
		return requestRepository.findAllByStatusRequest(statusRequest, eTypeRequest.CREATE_DATE);
	}

	@Override
    public List<Request> findAllRequestForBatch(final Long jobId, eStatusRequest statusRequest) {
        return requestRepository.findAllByJobIdAndStatusRequest(jobId, statusRequest, eTypeRequest.CREATE_DATE);
    }

    @Override
    public List<DayBatch> findAllDayBatchForBatch(final Long jobId) {
		List<UUID> ids = requestRepository.findAllByJobId(jobId, eTypeRequest.CREATE_DATE);
        return dayBatchRepository.findAllByRequestIDAndProcess(ids,false);
    }

    @Override
    public Country findCountry(Long id) {
        return countryRepository.getById(id);
    }

    @Override
    public void createDayBatch(DayBatch dayBatch) {
        dayBatchRepository.saveAndFlush(dayBatch);
    }

    @Override
    public void createDay(Day day) {
        dayRepository.saveAndFlush(day);
    }

    @Override
    public void saveRequest(Request request) {
        requestRepository.saveAndFlush(request);
    }
}
