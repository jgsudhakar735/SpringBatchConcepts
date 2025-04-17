package com.jgsudhakar.springboot.batch.skippolicy.config;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.exception.NameStartsWithException;
import com.jgsudhakar.springboot.batch.skippolicy.policy.CustomSkipPolicy;
import com.jgsudhakar.springboot.batch.skippolicy.processor.SkipPolicyFileItemProcessor;
import com.jgsudhakar.springboot.batch.skippolicy.reader.SkipPolicyFileItemReader;
import com.jgsudhakar.springboot.batch.skippolicy.writer.SkipPolicyFileItemWriter;
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
 * File    : com.jgsudhakar.springboot.batch.skippolicy.config.SkipPolicyJobConfig
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class SkipPolicyJobConfig {

    @Autowired
    private SkipPolicyFileItemReader skipPolicyFileItemReader;

    @Autowired
    private SkipPolicyFileItemProcessor skipPolicyFileItemProcessor;

    @Autowired
    private SkipPolicyFileItemWriter skipPolicyFileItemWriter;

    @Bean
    @Qualifier("skipJob")
    public Job skipJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("skipJob",jobRepository).
                start(processSkipJobStep(jobRepository,platformTransactionManager,skipPolicyFileItemReader,
                        skipPolicyFileItemProcessor,skipPolicyFileItemWriter)).
                build();
    }

    @Bean
    public Job skipPolicyJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("skipPolicyJob",jobRepository).
                start(processSkipPolicyJobStep(jobRepository,platformTransactionManager,skipPolicyFileItemReader,
                        skipPolicyFileItemProcessor,skipPolicyFileItemWriter)).
                build();
    }

    @Bean
    public Step processSkipJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                               ItemReader<EmpEntity> skipPolicyFileItemReader, ItemProcessor<EmpEntity,EmpEntity> skipPolicyFileItemProcessor,
                               ItemWriter<EmpEntity> skipPolicyFileItemWriter) {
                return new StepBuilder("processSkipJobStep",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                        reader(skipPolicyFileItemReader).
                        processor(skipPolicyFileItemProcessor).
                        writer(skipPolicyFileItemWriter).
                        faultTolerant().
                        skipLimit(1). // no of records which can be skip on failure
                        skip(NameStartsWithException.class). // skip on exception scenario
                        build();
    }

    @Bean
    public Step processSkipPolicyJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                   ItemReader<EmpEntity> skipPolicyFileItemReader, ItemProcessor<EmpEntity,EmpEntity> skipPolicyFileItemProcessor,
                                   ItemWriter<EmpEntity> skipPolicyFileItemWriter) {
        return new StepBuilder("processSkipPolicyJobStep",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                reader(skipPolicyFileItemReader).
                processor(skipPolicyFileItemProcessor).
                writer(skipPolicyFileItemWriter).
                faultTolerant().
                skipPolicy(new CustomSkipPolicy()).
                build();
    }
}
