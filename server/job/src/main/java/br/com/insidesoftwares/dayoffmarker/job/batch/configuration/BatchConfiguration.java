package br.com.insidesoftwares.dayoffmarker.job.batch.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
@EnableAsync
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final DayOffMarkerJobProperties dayOffMarkerJobProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(dayOffMarkerJobProperties.getCorePoolSize());
        executor.setMaxPoolSize(dayOffMarkerJobProperties.getMaxPoolSize());
        executor.setQueueCapacity(dayOffMarkerJobProperties.getQueueCapacity());
        executor.initialize();
        return executor;
    }

    @Bean
    public JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setTaskExecutor(taskExecutor());
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
