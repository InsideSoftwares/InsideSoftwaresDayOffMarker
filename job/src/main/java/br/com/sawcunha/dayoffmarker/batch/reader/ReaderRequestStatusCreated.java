package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class ReaderRequestStatusCreated implements ItemReader<Request> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private int nextRequestIndex;
    private List<Request> requests;

    @Override
    public Request read() {
        if(requests == null){
            requests = batchCreationDayService.findAllRequestForBatch(eStatusRequest.CREATED);
        }
        Request nextRequest = null;

        if (nextRequestIndex < requests.size()) {
            nextRequest = requests.get(nextRequestIndex++);
        } else {
            nextRequestIndex = 0;
        }

        return nextRequest;
    }
}
