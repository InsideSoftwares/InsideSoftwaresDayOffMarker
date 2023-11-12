package br.com.insidesoftwares.dayoffmarker.job.tag;

import br.com.insidesoftwares.dayoffmarker.commons.dto.tag.TagLinkUnlinkRequestDTO;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.commons.enumeration.TypeRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.day.Day;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.job.RequestJobBean;
import br.com.insidesoftwares.dayoffmarker.specification.job.tag.TagJobService;
import br.com.insidesoftwares.dayoffmarker.specification.service.tag.TagBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static br.com.insidesoftwares.dayoffmarker.domain.specification.tag.TagSpecification.findAllDayByTagLinkRequestDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagJobServiceBean extends RequestJobBean implements TagJobService {

    private final TagBatchService tagBatchService;

    @Override
    public void processLinkUnlinkTagDayBatch() {
        processLinkTagDayBatch();

        processUnlinkTagDayBatch();
    }

    private void processLinkTagDayBatch() {
        log.info("#processLinkTagDayBatch Starting link tag process in day");
        List<Request> requests = requestService.findAllRequestByTypeAndStatus(TypeRequest.LINK_TAG, StatusRequest.CREATED);

        startRequest(requests);

        requests.stream().parallel().forEach(this::linkTagDay);

        finalizeRequest(requests);
        log.info("#processLinkTagDayBatch Finalize link tag process in day");
    }

    private void processUnlinkTagDayBatch() {
        log.info("#processUnlinkTagDayBatch Starting unlink tag process in day");
        List<Request> requests = requestService.findAllRequestByTypeAndStatus(TypeRequest.UNLINK_TAG, StatusRequest.CREATED);

        startRequest(requests);

        requests.stream().parallel().forEach(this::unlinkTagDay);

        finalizeRequest(requests);
        log.info("#processUnlinkTagDayBatch Finalize unlink tag process in day");
    }

    private void linkTagDay(final Request request) {
        TagLinkUnlinkRequestDTO tagLinkRequestDTO = createTagLinkRequestDTO(request.getRequestParameter());

        Specification<Day> daySpecification = findAllDayByTagLinkRequestDTO(tagLinkRequestDTO);

        List<CompletableFuture<Boolean>> completableFutures = tagLinkRequestDTO.tagsID()
                .parallelStream()
                .map(tagId -> tagBatchService.linkDayTag(tagId, daySpecification)).toList();

        completableFutures.forEach( completableFuture -> {
            try {
                boolean isCreated = completableFuture.get();
                if(isCreated){
                    log.info("#linkTagDay Criado com sucesso");
                } else {
                    log.info("#linkTagDay Não foi criado com sucesso");
                }

            } catch (InterruptedException | ExecutionException e) {
                log.error("Error-linkTagDay - RequestID: {}", request.getId(), e);
            }
        });
    }

    private void unlinkTagDay(final Request request) {
        TagLinkUnlinkRequestDTO tagUnlinkRequestDTO = createTagLinkRequestDTO(request.getRequestParameter());

        Specification<Day> daySpecification = findAllDayByTagLinkRequestDTO(tagUnlinkRequestDTO);

        List<CompletableFuture<Boolean>> completableFutures = tagUnlinkRequestDTO.tagsID()
                .parallelStream()
                .map(tagId -> tagBatchService.unlinkDayTag(tagId, daySpecification)).toList();

        completableFutures.forEach( completableFuture -> {
            try {
                boolean isCreated = completableFuture.get();
                if(isCreated){
                    log.info("#unlinkTagDay Criado com sucesso");
                } else {
                    log.info("#unlinkTagDay Não foi criado com sucesso");
                }

            } catch (InterruptedException | ExecutionException e) {
                log.error("Error-unlinkTagDay - RequestID: {}", request.getId(), e);
            }
        });
    }

}
