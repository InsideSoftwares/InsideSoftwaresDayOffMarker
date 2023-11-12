package br.com.insidesoftwares.dayoffmarker.job.day;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.Month;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.RequestJobBean;
import br.com.insidesoftwares.dayoffmarker.specification.job.day.DayJobService;
import br.com.insidesoftwares.dayoffmarker.specification.service.day.DayBatchService;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DayJobServiceBean extends RequestJobBean implements DayJobService {

    private final DayBatchService dayBatchService;
    private final RequestCreationService requestCreationService;

    @Override
    public void processCreationDayBatch() {
        List<Request> requests = requestService.findAllRequestByTypeAndStatus(TypeRequest.REQUEST_CREATION_DATE, StatusRequest.CREATED);

        startRequest(requests);

        requests.stream().parallel().forEach(this::createDayBath);

        finalizeRequest(requests);
    }

    private void createDayBath(final Request request) {
        log.info("Starting the creation of the requests to create the Days.");

        Integer startYear = getStartYear(request.getRequestParameter());
        final Integer endYear = getEndYear(request.getRequestParameter());

        while (startYear <= endYear) {

            Integer year = startYear;
            List<CompletableFuture<Boolean>> completableFutures = Arrays.stream(Month.values()).parallel().map(month -> dayBatchService.createDayBatch(month, year)).toList();

            completableFutures.forEach( completableFuture -> {
                try {
                    boolean isCreated = completableFuture.get();
                    if(isCreated){
                        log.info("#createDayBath Criado com sucesso o Ano - {}", year);
                    } else {
                        log.info("#createDayBath Não foi criado do Ano - {}", year);
                    }

                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error-createDayBath - Year: {}", year, e);
                }
            });

            startYear++;
        }

        try {
            String requestId = requestCreationService.createHolidayRequest();
            log.info("Request holiday created: {}", requestId);
        } catch (Exception e) {
            log.error("It was not possible to create the Holidays.", e);
        }

        log.info("Finalizing the creation of requests to create days.");
    }
}
