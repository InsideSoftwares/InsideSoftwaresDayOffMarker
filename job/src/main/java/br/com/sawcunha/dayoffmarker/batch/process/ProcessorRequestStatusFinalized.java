package br.com.sawcunha.dayoffmarker.batch.process;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProcessorRequestStatusFinalized implements ItemProcessor<Request, Request> {

    @Override
    public Request process(Request request) {
        request.setStatusRequest(eStatusRequest.FINALIZED);
        request.setEndDate(LocalDateTime.now());
        return request;
    }

}
