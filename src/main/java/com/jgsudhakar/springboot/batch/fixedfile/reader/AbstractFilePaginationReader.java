package com.jgsudhakar.springboot.batch.fixedfile.reader;

import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import com.jgsudhakar.springboot.batch.fixedfile.dto.BaseDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.reader.AbstractFilePaginationReader
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Log4j2
public abstract class AbstractFilePaginationReader<T> extends AbstractPaginationReader<T> {

    private JobExecution jobExecution;

    private int startCount;

    private int pageSize;

    private FlatFileItemReader<T> reader;

    private int headerCount;
    private int trailerCount;
    private int dataCount;

    public void setParameters(StepExecution execution, String fileLocation, LineMapper<T> lineMapper){
        jobExecution = execution.getJobExecution();
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

        reader = new FlatFileItemReader<>();
        reader.setLineMapper(lineMapper());
        reader.setStrict(false);
        reader.setResource(new FileSystemResource("resources/" + fileLocation ));
        reader.open(new ExecutionContext());
        startCount=0;
    }

    @Override
    protected List<T> readPage() {
        log.info("Reading page with start index: {} and page size: {}", startCount, pageSize);
        List<T> dataList = new ArrayList<>();
        T data = null;
        try {
            do{
                 data = reader.read();
                if(data != null) {
                    // Check is this header or Data or Trailer
                    BaseDto baseDto = (BaseDto)data;
                    String recordType = baseDto.getRecordType();
                    if(recordType.equals("H")) {
                        log.info("Skipping Header Record: {}", data);
                        headerCount++;
                    } else if (recordType.equals("T")) {
                        log.info("Skipping Trailer Record: {}", data);
                        trailerCount++;
                    }else if(recordType.equals("D")) {
                        log.info("Reading Data Record: {}", data);
                        dataList.add(data);

                    }
                }

            }while (data != null);


        }catch (Exception e) {
            log.error("Error reading page: {}", e.getMessage(), e);
            return dataList;
        }

        return dataList;
    }

    public void afterStep(){
        log.info("After Step method called in AbstractFilePaginationReader");
        if(reader != null) {
            reader.close();
        }
        startCount = 0;
        pageSize = 0;
        initialCount = 0;
    }

    protected abstract LineMapper<T> lineMapper();
}
