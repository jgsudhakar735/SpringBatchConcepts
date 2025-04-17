package com.jgsudhakar.springboot.batch.resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.resource.StepResumeResource
 * Date    : 17-08-2024
 * Version : 1.0
 **************************************/
@RestController
@RequestMapping("/api/stepresume")
public class StepResumeResource {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("stepResumeJob")
    private Job stepResumeJob;

    @GetMapping("")
    public String processTaskLet(@RequestParam("jobId") String jobId) {
        JobParameters jobParameters = new JobParametersBuilder().addString("jobId", jobId)
                .toJobParameters();
        try {
            jobLauncher.run(stepResumeJob,jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
        return "SUCCESS";
    }
}
