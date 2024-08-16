package com.jgsudhakar.springboot.batch.decider.processor;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.decider.processor.DeciderFileItemProcessor
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class DeciderFileItemProcessor extends ItemListenerSupport<EmpEntity, EmpEntity> implements ItemProcessor<EmpEntity, EmpEntity> {

    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        log.info("File Processing Step Initialized");
        this.stepExecution = stepExecution;
        this.stepExecution.setExitStatus(new ExitStatus("QUITE"));
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
    public void afterProcess(EmpEntity item, EmpEntity result) {
        super.afterProcess(item, result);
        if(item.getFirstName().startsWith("S")){
            stepExecution.setExitStatus(new ExitStatus("NOTIFY"));
        }
    }
}
