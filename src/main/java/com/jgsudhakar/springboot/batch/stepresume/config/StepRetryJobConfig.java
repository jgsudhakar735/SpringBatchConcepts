package com.jgsudhakar.springboot.batch.stepresume.config;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.stepresume.decider.StepExecutionDecider;
import com.jgsudhakar.springboot.batch.stepresume.step_1.processor.Step1FileItemProcessor;
import com.jgsudhakar.springboot.batch.stepresume.step_1.reader.Step1FileItemReader;
import com.jgsudhakar.springboot.batch.stepresume.step_1.writer.Step1FileItemWriter;
import com.jgsudhakar.springboot.batch.stepresume.step_2.processor.Step2FileItemProcessor;
import com.jgsudhakar.springboot.batch.stepresume.step_2.reader.Step2FileItemReader;
import com.jgsudhakar.springboot.batch.stepresume.step_2.writer.Step2FileItemWriter;
import com.jgsudhakar.springboot.batch.stepresume.step_3.processor.Step3FileItemProcessor;
import com.jgsudhakar.springboot.batch.stepresume.step_3.reader.Step3FileItemReader;
import com.jgsudhakar.springboot.batch.stepresume.step_3.writer.Step3FileItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.config.StepRetryJobConfig
 * Date    : 17-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class StepRetryJobConfig {

    @Autowired
    private Step1FileItemReader step1FileItemReader;

    @Autowired
    private Step1FileItemProcessor step1FileItemProcessor;

    @Autowired
    private Step1FileItemWriter step1FileItemWriter;

    @Autowired
    private Step2FileItemReader step2FileItemReader;

    @Autowired
    private Step2FileItemProcessor step2FileItemProcessor;

    @Autowired
    private Step2FileItemWriter step2FileItemWriter;

    @Autowired
    private Step3FileItemReader step3FileItemReader;

    @Autowired
    private Step3FileItemProcessor step3FileItemProcessor;

    @Autowired
    private Step3FileItemWriter step3FileItemWriter;

    @Bean
    @Qualifier("stepResumeJob")
    public Job stepResumeJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("stepResumeJob",jobRepository).
                start(step1(jobRepository,platformTransactionManager,step1FileItemReader,
                        step1FileItemProcessor,step1FileItemWriter)).
                next(new StepExecutionDecider()).
                on(StepExecutionDecider.NOTIFY).
                to(step2(jobRepository,platformTransactionManager,step2FileItemReader,
                        step2FileItemProcessor,step2FileItemWriter)).
                on(StepExecutionDecider.QUIET).
                to(step3(jobRepository,platformTransactionManager,step3FileItemReader,
                        step3FileItemProcessor,step3FileItemWriter)).
                end().
                build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                               ItemReader<EmpEntity> step1FileItemReader, ItemProcessor<EmpEntity,EmpEntity> step1FileItemProcessor,
                               ItemWriter<EmpEntity> step1FileItemWriter) {
                return new StepBuilder("step1",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                        reader(step1FileItemReader).
                        processor(step1FileItemProcessor).
                        writer(step1FileItemWriter).
                        allowStartIfComplete(false).// if this flag is true then this will start when the job called and successfully completed too
                        build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                      ItemReader<EmpEntity> step2FileItemReader, ItemProcessor<EmpEntity,EmpEntity> step2FileItemProcessor,
                      ItemWriter<EmpEntity> step2FileItemWriter) {
        return new StepBuilder("step2",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                reader(step2FileItemReader).
                processor(step2FileItemProcessor).
                writer(step2FileItemWriter).
                allowStartIfComplete(false).// if this flag is true then this will start when the job called and successfully completed too
                startLimit(3).
                build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                      ItemReader<EmpEntity> step3FileItemReader, ItemProcessor<EmpEntity,EmpEntity> step3FileItemProcessor,
                      ItemWriter<EmpEntity> step3FileItemWriter) {
        return new StepBuilder("step3",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                reader(step3FileItemReader).
                processor(step3FileItemProcessor).
                writer(step3FileItemWriter).
                allowStartIfComplete(false).// if this flag is true then this will start when the job called and successfully completed too
                startLimit(3).// this job step will rin three time on the restart time. if this goes beyond it will throw error
                build();
    }


}
