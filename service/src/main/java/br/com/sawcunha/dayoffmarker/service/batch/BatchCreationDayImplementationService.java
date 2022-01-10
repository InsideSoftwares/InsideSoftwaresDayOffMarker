package br.com.sawcunha.dayoffmarker.service.batch;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
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

    private RequestRepository requestRepository;
    private RequestMapper requestMapper;
    private DayBatchRepository dayBatchRepository;
    private DayRepository dayRepository;
    private CountryRepository countryRepository;


    @Override
    public List<RequestDTO> findAllRequestDTOForBatch(List<UUID> requests, eStatusRequest statusRequest){
        return requestMapper.toDTOs(requestRepository.findAllByIdAndStatusRequest(requests, statusRequest));
    }

    @Override
    public List<Request> findAllRequestForBatch(List<UUID> requests, eStatusRequest statusRequest) {
        return requestRepository.findAllByIdAndStatusRequest(requests, statusRequest);
    }

    @Override
    public List<DayBatch> findAllDayBatchForBatch(List<UUID> requests) {
        return dayBatchRepository.findAllByRequestIDAndProcess(requests,false);
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
