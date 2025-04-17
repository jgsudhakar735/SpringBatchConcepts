package com.jgsudhakar.springboot.batch.stepresume.config;

import com.jgsudhakar.springboot.batch.stepresume.tasklet.Tasklet1;
import com.jgsudhakar.springboot.batch.stepresume.tasklet.Tasklet2;
import com.jgsudhakar.springboot.batch.stepresume.tasklet.Tasklet3;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.config.StepRetryTaskletConfig
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@Configuration
public class StepRetryTaskletConfig {

    @Bean
    @Qualifier("stepResumeTaskletJob")
    public Job stepResumeTaskletJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("stepResumeTaskletJob",jobRepository).
                start(tasklet1(jobRepository, platformTransactionManager)).
                next(tasklet2(jobRepository, platformTransactionManager)).
                next(tasklet3(jobRepository, platformTransactionManager)).
                build();
    }

    private Step tasklet3(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("tasklet3",jobRepository).
                tasklet(taskletThree(),platformTransactionManager).
                startLimit(3).
                build();
    }

    private Step tasklet2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("tasklet2",jobRepository).
                tasklet(taskletTwo(),platformTransactionManager).
                allowStartIfComplete(true).
                build();
    }

    private Step tasklet1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("tasklet1",jobRepository).
                tasklet(taskletOne(),platformTransactionManager).
                build();
    }

    private Tasklet1 taskletOne() {
        return new Tasklet1();
    }

    private Tasklet2 taskletTwo() {
        return new Tasklet2();
    }

    private Tasklet3 taskletThree() {
        return new Tasklet3();
    }
}
