package com.jgsudhakar.springboot.batch.resource;

import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import lombok.AllArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

/*************************************
 * This Class is used to
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.resource.BulkDataProcessResource
 * Date    : 01-06-2025
 * Version : 1.0
 **************************************/
@RestController
@RequestMapping("/api/fixedfile")
@AllArgsConstructor
public class FixedFileProcessResource {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("fixedFileBatchJob")
    private Job fixedFileBatchJob;

    @GetMapping("")
    public String processBulkDataFromDatabase() {
        JobParameters jobParameters = new JobParametersBuilder().addString(BatchConstants.COR_ID, String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        try {
            jobLauncher.run(fixedFileBatchJob,jobParameters);
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
