package com.jgsudhakar.springboot.batch.tasklet.processor;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.tasklet.processor.FileProcessorStep
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class FileProcessorStep implements Tasklet, StepExecutionListener {

    private List<EmpEntity> fileData;

    /**
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        if(CollectionUtils.isNotEmpty(fileData)) {
            for (EmpEntity empEntity:fileData) {
                log.info("Data Read from Previous step is :: {} ",empEntity.toString());
            }
        }
        return RepeatStatus.FINISHED;
    }

    /**
     * This method will be used to get the data from it's previous step and set to the current step objects.
     * @param stepExecution
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        this.fileData = (List<EmpEntity>) executionContext.get("lines");
        log.info("Data from Previous Step Has been set here");
    }

    /**
     * @param stepExecution
     * @return
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Processing the Data finished.");
        return ExitStatus.COMPLETED;
    }
}
