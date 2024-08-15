package com.jgsudhakar.springboot.batch.chunk.config;

import com.jgsudhakar.springboot.batch.chunk.processor.FileItemProcessor;
import com.jgsudhakar.springboot.batch.chunk.reader.FileItemReader;
import com.jgsudhakar.springboot.batch.chunk.writer.FileItemWriter;
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
 * File    : com.jgsudhakar.springboot.batch.chunk.config.ChunkJobConfig
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class ChunkJobConfig {

    @Autowired
    private FileItemReader itemReader;

    @Autowired
    private FileItemProcessor itemProcessor;

    @Autowired
    private FileItemWriter itemWriter;

    @Bean
    public Job chunkJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("chunkJob",jobRepository).
                start(processJobStep(jobRepository,platformTransactionManager,itemReader,
                        itemProcessor,itemWriter)).
                build();
    }

    @Bean
    public Step processJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                               ItemReader<EmpEntity> itemReader, ItemProcessor<EmpEntity,EmpEntity> itemProcessor,
                               ItemWriter<EmpEntity> itemWriter) {
                return new StepBuilder("processJobStep",jobRepository).<EmpEntity,EmpEntity>chunk(2,platformTransactionManager).
                        reader(itemReader).
                        processor(itemProcessor).
                        writer(itemWriter).
                        build();
    }
}
