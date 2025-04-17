package com.jgsudhakar.springboot.batch.partition.config;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.partition.processor.PartitionFileItemProcessor;
import com.jgsudhakar.springboot.batch.partition.reader.PartitionFileItemReader;
import com.jgsudhakar.springboot.batch.partition.writer.PartitionFileItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.partition.config.PartitionJobConfig
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class PartitionJobConfig {

    @Autowired
    private PartitionFileItemReader partitionFileItemReader;

    @Autowired
    private PartitionFileItemProcessor partitionFileItemProcessor;

    @Autowired
    private PartitionFileItemWriter partitionFileItemWriter;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Bean
    @Qualifier("partitionJob")
    public Job partitionJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("partitionJob",jobRepository).
                start(partitionStep(jobRepository,platformTransactionManager)).
                build();
    }

    @Bean
    public Step partitionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException, ParseException {
        return new StepBuilder("masterStep", jobRepository)
                .partitioner(slaveStep(jobRepository,transactionManager).getName(), partitioner())
                .partitionHandler(partitionerHandler(jobRepository,transactionManager))
                .build();
    }

    @Bean
    public CustomPartioner partitioner() {
        return new CustomPartioner();
    }

    private PartitionHandler partitionerHandler(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        // this should be based on the no of records you want to process max.ex. if the file is having the 1000 records, and you want to split to
        // 100 records per each then you should set the grid size as 10.
        taskExecutorPartitionHandler.setGridSize(9);
        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
        taskExecutorPartitionHandler.setStep(slaveStep(jobRepository,transactionManager));
        return taskExecutorPartitionHandler;
    }

    @Bean
    public Step slaveStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws UnexpectedInputException, ParseException {
        return new StepBuilder("slaveStep", jobRepository)
                .<EmpEntity, EmpEntity>chunk(1, transactionManager)
                .reader(partitionFileItemReader)
                .processor(partitionFileItemProcessor)
                .writer(partitionFileItemWriter)
                .build();
    }

    @Bean
    public Step partitionJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                               ItemReader<EmpEntity> partitionFileItemReader, ItemProcessor<EmpEntity,EmpEntity> partitionFileItemProcessor,
                               ItemWriter<EmpEntity> partitionFileItemWriter) {
                return new StepBuilder("partitionJobStep",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                        reader(partitionFileItemReader).
                        processor(partitionFileItemProcessor).
                        writer(partitionFileItemWriter).
                        build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        return taskExecutor;
    }
}
