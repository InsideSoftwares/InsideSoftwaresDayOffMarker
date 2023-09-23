package br.com.insidesoftwares.dayoffmarker.job.batch.write;

import br.com.insidesoftwares.dayoffmarker.domain.entity.request.Request;
import br.com.insidesoftwares.dayoffmarker.specification.service.request.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WriteRequest implements ItemWriter<Request> {

    private final RequestService requestService;

    @Override
    public void write(Chunk<? extends Request> requests)  {
        requests.forEach(request -> requestService.saveRequest(request) );
    }
}
