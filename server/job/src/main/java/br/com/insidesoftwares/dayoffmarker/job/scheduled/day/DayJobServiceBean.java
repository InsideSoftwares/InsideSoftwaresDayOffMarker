package br.com.insidesoftwares.dayoffmarker.job.scheduled.day;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.ParameterNotExistException;
import br.com.insidesoftwares.dayoffmarker.commons.exception.error.request.RequestConflictParametersException;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.scheduled.utils.RequestUtils;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestCreationService;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DayJobServiceBean {

    private final RequestService requestService;
    private final RequestCreationService requestCreationService;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @Autowired
    @Qualifier("jobCreatesDays")
    private Job jobCreatesDays;

    @Autowired
    @Qualifier("jobClearDayBatch")
    private Job jobClearDayBatch;

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void findRequestForCreatingDays() {

        List<Request> requests = requestService
                .findAllRequestByTypeAndStatus(TypeRequest.REQUEST_CREATION_DATE, StatusRequest.CREATED);

        requests.forEach(request -> {
            request.setStatusRequest(StatusRequest.WAITING);
            requestService.saveAndFlushRequest(request);
        });
        log.info("Updating request to be status WAITING.");
    }

    @Transactional(
            rollbackFor = {
                    ParameterNotExistException.class,
                    RequestConflictParametersException.class,
                    Exception.class
            }
    )
    public void runRequestForCreatingDays() {
        List<Request> requests = requestService
                .findAllRequestByTypeAndStatus(TypeRequest.REQUEST_CREATION_DATE, StatusRequest.WAITING);

        requests.forEach(request -> {
            request.setStatusRequest(StatusRequest.RUNNING);
            request.setStartDate(LocalDateTime.now());
            requestService.saveAndFlushRequest(request);
        });
        log.info("Updating request to be status RUNNING.");

        requests.forEach(request -> {
            try {
                createsRequestWithMonths(request);
                request.setStatusRequest(StatusRequest.SUCCESS);
                request.setEndDate(LocalDateTime.now());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                request.setStatusRequest(StatusRequest.ERROR);
                request.setEndDate(LocalDateTime.now());
            } finally {
                requestService.saveAndFlushRequest(request);
            }
        });
    }

    public void schedulingRunBatch() {
        try {
            if (requestService.existRequestByTypeAndStatusRequest(TypeRequest.CREATE_DATE, StatusRequest.CREATED)) {
                JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                        .getNextJobParameters(jobCreatesDays)
                        .toJobParameters();

                jobLauncher.run(jobCreatesDays, jobParameters);
                log.info("Starting batch for day creation.");
            }
        } catch (Exception e) {
            log.error("Error starting batch for day creation.", e);
        }
    }

    @Transactional(
            rollbackFor = {
                    ParameterNotExistException.class,
                    RequestConflictParametersException.class,
                    Exception.class
            }
    )
    public void createsRequestWithMonths(final Request request) {
        log.info("Starting the creation of the requests to create the Days.");

        Integer startYear = RequestUtils.getStartYear(request.getRequestParameter());
        Integer endYear = RequestUtils.getEndYear(request.getRequestParameter());

        while (startYear <= endYear) {

            int month = 1;

            while (month <= 12) {
                String requestId = requestCreationService.createDateRequest(request, month, startYear);
                log.info("Request day created: {}", requestId);
                month++;
            }
            startYear++;
        }
        log.info("Finalizing the creation of requests to create days.");
    }

    public void runClearDayBatch() {
        try {
            JobParameters jobParameters = new JobParametersBuilder(this.jobExplorer)
                    .getNextJobParameters(jobClearDayBatch)
                    .toJobParameters();

            jobLauncher.run(jobClearDayBatch, jobParameters);
            log.info("Starting batch for day creation.");
        } catch (Exception e) {
            log.error("Error starting batch for day creation.", e);
        }
    }
}
