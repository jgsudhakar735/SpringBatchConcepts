package com.jgsudhakar.springboot.batch.tasklet.config;

import com.jgsudhakar.springboot.batch.tasklet.processor.FileProcessorStep;
import com.jgsudhakar.springboot.batch.tasklet.reader.FileReaderStep;
import com.jgsudhakar.springboot.batch.tasklet.writer.FileWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.tasklet.config.TaskletJobConfig
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class TaskletJobConfig {

    @Bean
    @Qualifier("taskletJob")
    public Job taskletJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("taskletJob",jobRepository).
                start(readFileStep(jobRepository, platformTransactionManager)).
                next(processFileStep(jobRepository, platformTransactionManager)).
                next(writeFileStep(jobRepository, platformTransactionManager)).
                build();
    }

    @Bean
    public Step readFileStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("readFileStep",jobRepository).
            tasklet(linesReader(),platformTransactionManager).
            build();
    }

    @Bean
    public Step processFileStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("processFileStep",jobRepository).
                tasklet(processFile(),platformTransactionManager).
                build();
    }

    @Bean
    public Step writeFileStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("writeFileStep",jobRepository).
                tasklet(writeFile(),platformTransactionManager).
                build();
    }
    @Bean
    public FileReaderStep linesReader() {
        return new FileReaderStep();
    }

    @Bean
    public FileProcessorStep processFile() {
        return new FileProcessorStep();
    }

    @Bean
    public FileWriterStep writeFile() {
        return new FileWriterStep();
    }
}
