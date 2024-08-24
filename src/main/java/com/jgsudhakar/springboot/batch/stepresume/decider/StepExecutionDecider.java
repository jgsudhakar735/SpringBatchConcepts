package com.jgsudhakar.springboot.batch.stepresume.decider;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.decider.StepExecutionDecider
 * Date    : 22-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class StepExecutionDecider implements JobExecutionDecider {

    public static final String NOTIFY = "NOTIFY";
    public static final String QUIET = "QUIET";

    // this can be custom code where you can configure ,whether you want to notify or not
    private boolean shouldNotify() {
        return true;
    }

    /**
     * @param jobExecution
     * @param stepExecution
     * @return
     */
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        log.info("In the FLow Decider :: {} ",stepExecution.getExitStatus());
        if(shouldNotify()) {
            return new FlowExecutionStatus(NOTIFY);
        }else {
            return new FlowExecutionStatus(QUIET);
        }
    }
}
