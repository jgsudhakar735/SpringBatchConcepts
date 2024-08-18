package com.jgsudhakar.springboot.batch.stepresume.step_3.processor;

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
 * File    : com.jgsudhakar.springboot.batch.stepresume.step_3.processor.Step3FileItemProcessor
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class Step3FileItemProcessor implements ItemProcessor<EmpEntity,EmpEntity>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step3FileItemProcessor#beforeStep File Processing Step Initialized");
    }

    /**
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public EmpEntity process(EmpEntity item) throws Exception {
        log.info("Step3FileItemProcessor # process Data Received From Reader is :: {} ", item);
        return item;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();
        log.info("Step3FileItemProcessor # afterStep Status from Previous Reader :: {} ",exitStatus.getExitCode());
        return ExitStatus.COMPLETED;
    }
}
