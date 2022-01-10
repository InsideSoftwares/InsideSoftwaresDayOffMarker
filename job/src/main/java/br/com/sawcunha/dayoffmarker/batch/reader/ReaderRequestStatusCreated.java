package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.specification.batch.BatchCreationDayService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@StepScope
public class ReaderRequestStatusCreated implements ItemReader<Request> {

    @Autowired
    private BatchCreationDayService batchCreationDayService;

    private int nextRequestIndex;
    private List<Request> requests;
    private List<UUID> uuid;

    @Value("#{jobParameters['List']}")
    public void setFileName(final String name) {
        uuid = new ArrayList<>();
        Arrays.stream(name.split(",")).forEach(s -> {
            uuid.add(UUID.fromString(s.trim()));
        });
    }

    @Override
    public Request read() throws Exception {
        if(requests == null){
            requests = batchCreationDayService.findAllRequestForBatch(
                    uuid,
                    eStatusRequest.CREATED
            );
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
