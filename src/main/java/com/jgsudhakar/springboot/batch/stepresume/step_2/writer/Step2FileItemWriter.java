package com.jgsudhakar.springboot.batch.stepresume.step_2.writer;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.step_2.writer.Step2FileItemReader
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class Step2FileItemWriter implements ItemWriter<EmpEntity>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
       log.info("Step2FileItemProcessor#beforeStep Writing the Data to File / DB initialized");
    }

    /**
     * @param chunk
     * @throws Exception
     */
    @Override
    public void write(Chunk<? extends EmpEntity> chunk) throws Exception {
        log.info("Step2FileItemProcessor#before Writing the Data of  :: ",chunk.size());
        for (EmpEntity empEntity: chunk) {
            log.info("Data in the Writer is  :: {} ",empEntity);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step2FileItemProcessor#afterStep");
        return ExitStatus.COMPLETED;
    }
}
