package com.jgsudhakar.springboot.batch.partition.reader;

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
 * File    : com.jgsudhakar.springboot.batch.partition.reader.PartitionFileItemReader
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class PartitionFileItemReader implements ItemReader<EmpEntity>, StepExecutionListener {

    private FileUtils fileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fileUtils = new FileUtils("samplefile.csv");
        log.info("Initializing the File Path and Creating Object");
        String  partitionName = stepExecution.getStepName();
        Integer minValue = stepExecution.getExecutionContext().getInt("minValue");
        Integer maxValue = stepExecution.getExecutionContext().getInt("maxValue");
        log.info("Step / Partition Name {} ",partitionName);
        log.info("Min Values {} ",minValue);
        log.info("Max Values {} ",maxValue);
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
            log.debug("Read line: " + line);
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(null != fileUtils)
            fileUtils.closeReader();
        return ExitStatus.COMPLETED;
    }
}
