package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
import br.com.sawcunha.dayoffmarker.commons.exception.error.DayOffMarkerGenericException;
import br.com.sawcunha.dayoffmarker.entity.Request;
import br.com.sawcunha.dayoffmarker.specification.service.RequestService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class ReaderRequestStatusRunning implements ItemReader<Request> {

    @Autowired
    private RequestService requestService;

    private int nextRequestIndex;
    private List<Request> requests;

	private Long jobId;

	@BeforeStep
	public void getInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		this.jobId = jobExecution.getJobId();
	}

    @Override
    public Request read() throws DayOffMarkerGenericException {
        if(requests == null){
            requests = requestService.findAllRequestByJobIDAndStatus(jobId, eStatusRequest.RUNNING);
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
