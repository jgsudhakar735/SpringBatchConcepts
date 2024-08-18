package com.jgsudhakar.springboot.batch.stepresume.step_3.reader;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.stepresume.util.StepResumeConstants;
import com.jgsudhakar.springboot.batch.tasklet.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
 * File    : com.jgsudhakar.springboot.batch.stepresume.step_3.reader.Step3FileItemReader
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class Step3FileItemReader implements ItemReader<EmpEntity>, StepExecutionListener {

    private FileUtils fileUtils;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fileUtils = new FileUtils("samplefile.csv");
        log.info("Initializing the File Path and Creating Object");
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
        String stepName = stepExecution.getStepName();
        if(stepName.equalsIgnoreCase("processResumeJobStep")) {
            if (null != fileUtils)
                fileUtils.closeReader();
            return ExitStatus.COMPLETED;
        }else if(stepName.equalsIgnoreCase("processResumeOnFailureJobStep")) {
            if (null != fileUtils)
                fileUtils.closeReader();
           if(StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase(StringUtils.EMPTY)) {
               StepResumeConstants.CALLED_STEP_3 =  StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase( "YES")?StringUtils.EMPTY: "YES";
               throw new RuntimeException("BATCH_JOB_STEP_FAILING");
           }else {
               StepResumeConstants.CALLED_STEP_3 =  StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase( "YES")?StringUtils.EMPTY: "YES";
               return ExitStatus.COMPLETED;
           }
        }else {
            if (null != fileUtils)
                fileUtils.closeReader();
            if(StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase(StringUtils.EMPTY)) {
                StepResumeConstants.CALLED_STEP_3 =  StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase( "YES")?StringUtils.EMPTY: "YES";
                throw new RuntimeException("BATCH_JOB_STEP_FAILING"+stepName);
            }else {
                StepResumeConstants.CALLED_STEP_3 =  StepResumeConstants.CALLED_STEP_3.equalsIgnoreCase( "YES")?StringUtils.EMPTY: "YES";
                return ExitStatus.COMPLETED;
            }
        }
    }
}
