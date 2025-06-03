package com.jgsudhakar.springboot.batch.fixedfile.reader;

import com.jgsudhakar.springboot.batch.fixedfile.dto.HeaderDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.TrailerDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.reader.FixedFileItemReader
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class FixedFileItemReader extends AbstractFilePaginationReader<InputDataDto> {

    @Autowired
    private FixedLengthTokenizer dataTokenizer;

    @Autowired
    private FixedLengthTokenizer headerTokenizer;

    @Autowired
    private FixedLengthTokenizer trailerTokenizer;
    @Autowired
    private FieldSetMapper<InputDataDto> fieldSetMapper;

    @Autowired
    private FieldSetMapper<HeaderDto> headerFieldSetMapper;

    @Autowired
    private FieldSetMapper<TrailerDto> trailerFieldSetMapper;

    /**
     * This method is called before the step execution to set the parameters for the reader.
     * It initializes the file location and line mapper.
     *
     * @param execution StepExecution object containing the step execution context
     */
    @BeforeStep
    public void setParameters(StepExecution execution){
        log.info("Before Step method called in FixedFileItemReader");
        super.setParameters(execution, "D:\\Learning\\GitHub\\SpringBatchConcepts\\src\\main\\resources\\InputFile.txt", lineMapper(),2);
    }


    @AfterStep
    public void afterStep(){
    super.afterStep();
        log.info("After Step method called in FixedFileItemReader");
        super.afterStep();
    }

    /**
     * @return
     */
    @Override
    protected LineMapper<InputDataDto> lineMapper() {
        PatternMatchingCompositeLineMapper lineMapper =
                new PatternMatchingCompositeLineMapper();

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);
        tokenizers.put("H*", headerTokenizer);
        tokenizers.put("D*", dataTokenizer);
        tokenizers.put("T*", trailerTokenizer);

        lineMapper.setTokenizers(tokenizers);

        Map<String, FieldSetMapper> mappers = new HashMap<>(2);
        mappers.put("D*", fieldSetMapper);
        mappers.put("H*", headerFieldSetMapper);
        mappers.put("T*", trailerFieldSetMapper);

        lineMapper.setFieldSetMappers(mappers);

        return lineMapper;
    }

}
