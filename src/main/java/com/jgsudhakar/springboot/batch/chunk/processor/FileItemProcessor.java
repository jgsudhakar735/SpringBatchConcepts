package com.jgsudhakar.springboot.batch.chunk.processor;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.chunk.processor.FileItemProcessor
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class FileItemProcessor implements ItemProcessor<EmpEntity,EmpEntity>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("File Processing Step Initialized");
    }

    /**
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public EmpEntity process(EmpEntity item) throws Exception {
        log.info("Data Received From Reader is ::", item.toString());
        return item;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
