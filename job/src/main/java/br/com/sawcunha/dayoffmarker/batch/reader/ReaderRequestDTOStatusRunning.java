package br.com.sawcunha.dayoffmarker.batch.reader;

import br.com.sawcunha.dayoffmarker.commons.dto.batch.RequestDTO;
import br.com.sawcunha.dayoffmarker.commons.enums.eStatusRequest;
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
public class ReaderRequestDTOStatusRunning implements ItemReader<RequestDTO> {

    @Autowired
    private RequestService requestService;

    private int nextRequest;
    private List<RequestDTO> requestDTOs;

	private Long jobId;

	@BeforeStep
	public void getInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		this.jobId = jobExecution.getJobId();
	}

    @Override
    public RequestDTO read() throws Exception {
        if(requestDTOs == null){
            requestDTOs = requestService.findAllRequestDTOForBatch(jobId, eStatusRequest.RUNNING);
        }
        RequestDTO nextRequestDto = null;

        if (nextRequest < requestDTOs.size()) {
            nextRequestDto = requestDTOs.get(nextRequest++);
        } else {
            nextRequest = 0;
        }

        return nextRequestDto;
    }
}
