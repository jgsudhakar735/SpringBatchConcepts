package com.jgsudhakar.springboot.batch.tasklet.reader;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.tasklet.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.tasklet.reader.FileReaderStep
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class FileReaderStep implements Tasklet, StepExecutionListener {

    private List<EmpEntity> fileData;
    private FileUtils fileUtils;
    /**
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        EmpEntity empEntity = fileUtils.readLine();
        while (null != empEntity) {
            fileData.add(empEntity);
            log.info("Data Read from the File ::",empEntity.toString());
            empEntity = fileUtils.readLine();
        }
        return RepeatStatus.FINISHED;
    }

    /**
     * This method will be used to initialize the required objects which can be used in the execute method.
     * @param stepExecution
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Before Reading the File Data");
        // Initializing the required objects
        fileData = new ArrayList<>();
        fileUtils = new FileUtils("samplefile.csv");
    }

    /**
     * @param stepExecution
     * @return
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fileUtils.closeReader();
        stepExecution.getJobExecution().getExecutionContext().put("lines",this.fileData);
        log.info("File Data reader End");
        return ExitStatus.COMPLETED;
    }
}
