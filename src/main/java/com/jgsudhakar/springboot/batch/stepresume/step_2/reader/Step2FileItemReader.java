package com.jgsudhakar.springboot.batch.stepresume.step_2.reader;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.tasklet.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.step_2.reader.Step2FileItemReader
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class Step2FileItemReader implements ItemReader<EmpEntity>, StepExecutionListener {

    private FileUtils fileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fileUtils = new FileUtils("samplefile.csv");
        log.info("Step2FileItemProcessor#beforeStep Initializing the File Path and Creating Object");
    }

    /**
     * @return
     * @throws Exception
     * @throws UnexpectedInputException
     * @throws ParseException
     * @throws NonTransientResourceException
     */
    @Override
    public EmpEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        EmpEntity line = fileUtils.readLine();
        if (line != null)
            log.debug("Step2FileItemProcessor#read  line: " + line);
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step2FileItemProcessor#afterStep");
        return ExitStatus.COMPLETED;
    }
}
