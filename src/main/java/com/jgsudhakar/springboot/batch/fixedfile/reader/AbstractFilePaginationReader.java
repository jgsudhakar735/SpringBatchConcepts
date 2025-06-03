package com.jgsudhakar.springboot.batch.fixedfile.reader;

import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import com.jgsudhakar.springboot.batch.fixedfile.dto.BaseDto;
import lombok.extern.log4j.Log4j2;
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

    private int endCount;

    private int currentLine;

    private FlatFileItemReader<T> reader;

    private int headerCount;
    private int trailerCount;

    private int initialPageSize;
    public void setParameters(StepExecution execution, String fileLocation, LineMapper<T> lineMapper,Integer pageSize){
        jobExecution = execution.getJobExecution();
        String corId = jobExecution.getJobParameters().getString(BatchConstants.COR_ID);
        log.info("Correlation ID: {}", corId);
        log.info("Page Size: {}", pageSize);
        setPageSize(pageSize);

        reader = new FlatFileItemReader<>();
        reader.setLineMapper(lineMapper());
        reader.setStrict(false);
        reader.setResource(new FileSystemResource(fileLocation ));
        reader.open(new ExecutionContext());
        endCount=pageSize;
        currentLine = 0;
        initialPageSize = pageSize;
    }

    @Override
    protected List<T> readPage() {
        log.info("Reading page with start index: {} and page size: {}", currentLine, endCount);
        List<T> dataList = new ArrayList<>();
        T data = null;
        log.info("Current Line and Page Size: {}, {}", currentLine, endCount);
        try {
            do{
               // pagination logic
                 data = reader.read();
                 currentLine++;

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
                if(currentLine == endCount) {
                    endCount = endCount+initialPageSize;
                    setPageSize(endCount);
                    log.info("Reached end of Current page size: {}. Incrementing to Next Page Size {}", currentLine, endCount);

                }

            }while (data != null && currentLine <= endCount);


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
    }

    protected abstract LineMapper<T> lineMapper();
}
