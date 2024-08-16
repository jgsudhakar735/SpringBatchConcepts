package com.jgsudhakar.springboot.batch.decider.condition;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.decider.condition.FlowDecider
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
public class FlowDecider implements JobExecutionDecider {

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
        if(shouldNotify()) {
            return new FlowExecutionStatus("NOTIFY");
        }else {
            return new FlowExecutionStatus("QUITE");
        }
    }
}
