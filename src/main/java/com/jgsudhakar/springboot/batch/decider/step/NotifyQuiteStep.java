package com.jgsudhakar.springboot.batch.decider.step;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.decider.step.NotifyQuiteStep
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class NotifyQuiteStep implements Tasklet {
    /**
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("In the Notification Step Status Quite ::",chunkContext.getStepContext().getStepExecution().getExitStatus());
        return RepeatStatus.FINISHED;
    }
}
