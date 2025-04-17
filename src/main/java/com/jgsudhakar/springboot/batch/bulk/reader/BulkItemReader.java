package com.jgsudhakar.springboot.batch.bulk.reader;

import com.jgsudhakar.springboot.batch.bulk.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import com.jgsudhakar.springboot.batch.service.EmpService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.bulk.reader.BulkItemReader
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class BulkItemReader extends AbstractPaginationReader<InputDataDto> {

    private int startCount;

    private int pageSize;

    @Autowired
    private EmpService empService;

    @BeforeStep
    public void setParameters(StepExecution execution){
        JobExecution jobExecution = execution.getJobExecution();
        String corId = jobExecution.getJobParameters().getString(BatchConstants.COR_ID);
        String pageSizeFromInput = jobExecution.getJobParameters().getString(BatchConstants.PAGE_SIZE);
        log.info("Correlation ID: {}", corId);
        log.info("Page Size: {}", pageSizeFromInput);
        if(StringUtils.isNotBlank(pageSizeFromInput)) {
            this.pageSize = Integer.parseInt(pageSizeFromInput);
        } else {
            this.pageSize = 2;
        }
        setPageSize(pageSize);
        startCount=0;
    }

    /**
     * @return
     */
    @Override
    protected List<InputDataDto> readPage() {
        List<InputDataDto> empList = new ArrayList<>();
        try {
            log.info("Reading page with start index: {} and page size: {}", startCount, pageSize);
            List<InputDataDto> empListFromDb = empService.fetchEmpList(getPageSize(), startCount);
            empList.addAll(empListFromDb);
            startCount = startCount+ empListFromDb.size();
        } catch (Exception e) {
            log.error("Error while fetching total records: {}", e.getMessage());
        }

        return empList;
    }

    @AfterStep
    public void afterStep(){
        log.info("Total Records Read: {}", startCount);
        log.info("Page Size: {}", pageSize);
    }
}
