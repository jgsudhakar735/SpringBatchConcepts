package com.jgsudhakar.springboot.batch.fixedfile.writer;

import com.jgsudhakar.springboot.batch.fixedfile.dto.OutputDataDto;
import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.writer.FixedFileItemWriter
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class FixedFileItemWriter implements ItemWriter<OutputDataDto> {

    private int totalRecords = 0;

    @BeforeStep
    public void setParameters(StepExecution execution){
        JobExecution jobExecution = execution.getJobExecution();
        String corId = jobExecution.getJobParameters().getString(BatchConstants.COR_ID);
        totalRecords= 0;
        log.info("Correlation ID: {}", corId);
    }

    @Override
    public void write(Chunk<? extends OutputDataDto> chunk) throws Exception {
        log.info("Writing chunk of size: {}", chunk.size());
        totalRecords = totalRecords + chunk.size();
        for (OutputDataDto item : chunk) {
            log.info("Writing item: {}", item);
            // Here you can write the item to the database or any other destination
            // For example, you can use a repository to save the item
            // empRepository.save(item);
        }
    }

    @AfterStep
    public void afterStep(){
        log.info("Item Writer After Step Total Records :: {}",totalRecords);
    }
}
