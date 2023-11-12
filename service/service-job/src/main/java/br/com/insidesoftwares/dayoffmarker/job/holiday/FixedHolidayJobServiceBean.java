package br.com.insidesoftwares.dayoffmarker.job.holiday;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.holiday.FixedHoliday;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.domain.repository.holiday.FixedHolidayRepository;
import br.com.insidesoftwares.dayoffmarker.job.RequestJobBean;
import br.com.insidesoftwares.dayoffmarker.specification.job.holiday.FixedHolidayJobService;
import br.com.insidesoftwares.dayoffmarker.specification.service.holiday.FixedHolidayBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.StreamSupport;

import static br.com.insidesoftwares.dayoffmarker.job.RequestParametersUtils.getFixedHolidayID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixedHolidayJobServiceBean extends RequestJobBean implements FixedHolidayJobService {

    private final FixedHolidayRepository fixedHolidayRepository;
    private final FixedHolidayBatchService fixedHolidayBatchService;

    @Override
    public void processFixedHolidayBatch() {
        log.info("#processFixedHolidayBatch Starting create holiday");
        List<Request> requests = requestService.findAllRequestByTypeAndStatus(TypeRequest.CREATE_HOLIDAY, StatusRequest.CREATED);

        startRequest(requests);

        requests.parallelStream().forEach(this::createHoliday);

        finalizeRequest(requests);
        log.info("#processLinkTagDayBatch Finalize crete Holiday");
    }

    public void createHoliday(final Request request) {
        log.info("#createHoliday - Create the Holidays.");
        UUID fixedHolidayID = getFixedHolidayID(request.getRequestParameter());

        Iterable<FixedHoliday> fixedHolidays = fixedHolidayRepository.findAllByFixedHolidayIdAndIsEnable(fixedHolidayID, true);

        List<CompletableFuture<Boolean>> completableFutures = StreamSupport.stream(fixedHolidays.spliterator(), true)
                .map(fixedHolidayBatchService::createHolidayBatch)
                .toList();

        completableFutures.forEach( completableFuture -> {
            try {
                boolean isCreated = completableFuture.get();
                if(isCreated){
                    log.info("#createHoliday Criado com sucesso");
                } else {
                    log.info("#createHoliday Não foi criado com sucesso");
                }

            } catch (InterruptedException | ExecutionException e) {
                log.error("Error-createHoliday - RequestID: {}", request.getId(), e);
            }
        });
    }
}
