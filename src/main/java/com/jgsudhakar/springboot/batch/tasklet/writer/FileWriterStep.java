package com.jgsudhakar.springboot.batch.tasklet.writer;

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
 * File    : com.jgsudhakar.springboot.batch.tasklet.writer.FileWriterStep
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class FileWriterStep implements Tasklet, StepExecutionListener {

    private List<EmpEntity> fileData;

    /**
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Writing the File Data to Logs / File");
        if (CollectionUtils.isNotEmpty(fileData)) {
            fileData.forEach(data -> {
                log.info("File Data in the Writer :: {} ",data.toString());
            });

        }
        return RepeatStatus.FINISHED;
    }

    /**
     * @param stepExecution
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        this.fileData = (List<EmpEntity>) executionContext.get("lines");
    }

    /**
     * @param stepExecution
     * @return
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.debug("Lines Writer ended.");
        return ExitStatus.COMPLETED;
    }
}
