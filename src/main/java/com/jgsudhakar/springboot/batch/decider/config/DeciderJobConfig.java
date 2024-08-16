package com.jgsudhakar.springboot.batch.decider.config;

import com.jgsudhakar.springboot.batch.decider.step.NotifyQuiteStep;
import com.jgsudhakar.springboot.batch.decider.step.NotifyStep;
import com.jgsudhakar.springboot.batch.decider.condition.FlowDecider;
import com.jgsudhakar.springboot.batch.decider.processor.DeciderFileItemProcessor;
import com.jgsudhakar.springboot.batch.decider.reader.DeciderFileItemReader;
import com.jgsudhakar.springboot.batch.decider.writer.DeciderFileItemWriter;
import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.dcider.config.DeciderJobConfig
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class DeciderJobConfig {

    @Autowired
    private DeciderFileItemReader deciderFileItemReader;

    @Autowired
    private DeciderFileItemProcessor deciderFileItemProcessor;

    @Autowired
    private DeciderFileItemWriter deciderFileItemWriter;

    @Bean
    public Job deciderChunkJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                        Step notificationStep,Step notificationQuiteStep) {
        Step step = processDecideJobStep(jobRepository,platformTransactionManager,deciderFileItemReader,
                deciderFileItemProcessor,deciderFileItemWriter);
        return new JobBuilder("deciderChunkJob",jobRepository).
                start(step).
                next(new FlowDecider()).
                on(FlowDecider.NOTIFY).
                to(notificationStep).
                from(new FlowDecider()).
                on("*").
                to(notificationQuiteStep(jobRepository,platformTransactionManager)).
                end().
                build();
    }

    @Bean
    public Step processDecideJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                               ItemReader<EmpEntity> deciderFileItemReader, ItemProcessor<EmpEntity,EmpEntity> deciderFileItemProcessor,
                               ItemWriter<EmpEntity> deciderFileItemWriter) {
                return new StepBuilder("processDecideJobStep",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                        reader(deciderFileItemReader).
                        processor(deciderFileItemProcessor).
                        writer(deciderFileItemWriter).
                        build();
    }

    @Bean
    public Step notificationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("notificationStep", jobRepository)
                .tasklet(new NotifyStep(), transactionManager)
                .build();
    }

    @Bean
    public Step notificationQuiteStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("notificationQuiteStep", jobRepository)
                .tasklet(new NotifyQuiteStep(), transactionManager)
                .build();
    }
}
