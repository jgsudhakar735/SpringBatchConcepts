package com.jgsudhakar.springboot.batch.fixedfile.config;

import com.jgsudhakar.springboot.batch.fixedfile.dto.HeaderDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.TrailerDto;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.config.FixedFileBeanConfig
 * Date    : 31-05-2025
 * Version : 1.0
 **************************************/
@Configuration
public class FixedFileBeanConfig {

    /**
     * FixedLengthTokenizer is used to parse fixed-length records.
     * Each field is defined by its start and end positions.
     * The names of the fields are specified in the setNames method.
     */
    @Bean("dataTokenizer")
    public FixedLengthTokenizer dataTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames("recordType","firstName", "lastName", "village", "mandal","disctrict");
        tokenizer.setColumns(new Range(1, 1),
                new Range(2, 6),
                new Range(7, 30),
                new Range(21, 31),
                new Range(32, 40),
                new Range(41, 48)
        );

        return tokenizer;
    }

    // Header Tokenizer
    @Bean("headerTokenizer")
    public FixedLengthTokenizer headerTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames("recordType","headerData");
        tokenizer.setColumns(new Range(1,1),new Range(2, 50)); // Adjust the range as per your header length
        return tokenizer;
    }
    // footer Tokenizer
    @Bean("trailerTokenizer")
    public FixedLengthTokenizer trailerTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setNames("recordType","trailerData");
        tokenizer.setColumns(new Range(1,1),new Range(2, 50)); // Adjust the range as per your footer length
        return tokenizer;
    }

    // fieldSetMapper Bean
    @Bean
    public FieldSetMapper<HeaderDto> headerFileSetMapper() {
        BeanWrapperFieldSetMapper<HeaderDto> headerSetMapper = new BeanWrapperFieldSetMapper<>();
        headerSetMapper.setTargetType(HeaderDto.class);
        return headerSetMapper;
    }

    // Trailer FieldSetMapper
    @Bean
    public FieldSetMapper<TrailerDto> trailerFieldSetMapper() {
        BeanWrapperFieldSetMapper<TrailerDto> trailerSetMapper = new BeanWrapperFieldSetMapper<>();
        trailerSetMapper.setTargetType(TrailerDto.class);
        return trailerSetMapper;
    }

    /**
     * FieldSetMapper is used to map the fields from the FixedLengthTokenizer
     * to the InputDataDto object.
     * The prototype bean is used to create a new instance for each record.
     */
    @Bean
    public FieldSetMapper fieldSetMapper() {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setPrototypeBeanName("inputDataDto");
        return fieldSetMapper;
    }

    @Bean("inputDataDto")
    @Scope("prototype")
    public InputDataDto inputDataDto() {
        return new InputDataDto();
    }

    @Bean("headerDto")
    @Scope("prototype")
    public HeaderDto headerDto() {
        return new HeaderDto();
    }

    @Bean("trailerDto")
    @Scope("prototype")
    public TrailerDto trailerDto() {
        return new TrailerDto();
    }


}
