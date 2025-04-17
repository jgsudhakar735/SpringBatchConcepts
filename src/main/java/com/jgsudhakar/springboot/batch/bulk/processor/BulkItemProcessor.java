package com.jgsudhakar.springboot.batch.bulk.processor;

import com.jgsudhakar.springboot.batch.bulk.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.bulk.dto.OutputDataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.bulk.processor.BulkItemWriter
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class BulkItemProcessor implements ItemProcessor<InputDataDto, OutputDataDto> {

    /**
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public OutputDataDto process(InputDataDto item) throws Exception {
        log.info("Processing item: {}", item);
        OutputDataDto outputDataDto = new OutputDataDto();
        outputDataDto.setId(item.getId());
        outputDataDto.setFirstName(item.getFirstName());
        outputDataDto.setLastName(item.getLastName());
        return outputDataDto;
    }
}
