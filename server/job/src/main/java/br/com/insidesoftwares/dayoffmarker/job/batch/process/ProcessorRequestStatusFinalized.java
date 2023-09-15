package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProcessorRequestStatusFinalized implements ItemProcessor<Request, Request> {

    @Override
    public Request process(Request request) {
        request.setStatusRequest(StatusRequest.FINALIZED);
        request.setEndDate(LocalDateTime.now());
        return request;
    }

}
