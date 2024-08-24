package com.jgsudhakar.springboot.batch.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.resource.StepResumeTaskletResource
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
@RestController
@RequestMapping("/api/stepresume/tasklet")
public class StepResumeTaskletResource {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private Job stepResumeTaskletJob;

    @GetMapping("")
    public String handle(@RequestParam("jobId") String jobId) throws Exception {


        try {
            JobParameters jobParameters = new JobParametersBuilder().addString("jobId", jobId)
                    .toJobParameters();
            jobLauncher.run(stepResumeTaskletJob, jobParameters);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return "Done!";
    }
}
