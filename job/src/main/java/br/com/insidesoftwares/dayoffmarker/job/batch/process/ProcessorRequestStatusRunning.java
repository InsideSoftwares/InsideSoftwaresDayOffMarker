package br.com.insidesoftwares.dayoffmarker.job.batch.process;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.StatusRequest;
import br.com.insidesoftwares.dayoffmarker.entity.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProcessorRequestStatusRunning implements ItemProcessor<Request, Request> {

	private Long jobId;

	@BeforeStep
	public void getInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		this.jobId = jobExecution.getJobId();
	}

    @Override
    public Request process(Request request) {
        request.setStartDate(LocalDateTime.now());
        request.setStatusRequest(StatusRequest.RUNNING);
		request.setJobId(jobId);
        return request;
    }

}
