package com.jgsudhakar.springboot.batch.fixedfile.config;

import com.jgsudhakar.springboot.batch.fixedfile.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.OutputDataDto;
import com.jgsudhakar.springboot.batch.fixedfile.processor.FixedFileItemProcessor;
import com.jgsudhakar.springboot.batch.fixedfile.reader.FixedFileItemReader;
import com.jgsudhakar.springboot.batch.fixedfile.writer.FixedFileItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.config.FixedFileBatchConfig
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Configuration
public class FixedFileBatchConfig {

    @Autowired
    private FixedFileItemReader fixedFileItemReader;

    @Autowired
    private FixedFileItemProcessor fixedFileItemProcessor;

    @Autowired
    private FixedFileItemWriter fixedFileItemWriter;

    // Define your batch job configuration here
    @Bean
    @Qualifier("fixedFileBatchJob")
    public Job fixedFileBatchJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("fixedFileBatchJob",jobRepository).
                start(fixedFileBatchJobStep(jobRepository,platformTransactionManager)).
                build();
    }

    @Bean
    public Step fixedFileBatchJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("fixedFileBatchJobStep",jobRepository).<InputDataDto, OutputDataDto>chunk(2,platformTransactionManager).
                reader(fixedFileItemReader).
                processor(fixedFileItemProcessor).
                writer(fixedFileItemWriter).
                build();
    }


}
