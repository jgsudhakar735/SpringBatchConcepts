package com.jgsudhakar.springboot.batch.fixedfile.processor;

import com.jgsudhakar.springboot.batch.fixedfile.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.fixedfile.dto.OutputDataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.processor.FixedFileItemWriter
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
@Log4j2
public class FixedFileItemProcessor implements ItemProcessor<InputDataDto, OutputDataDto> {

    /**
     * @param item
     * @return
     * @throws Exception
     */
    @Override
    public OutputDataDto process(InputDataDto item) throws Exception {
        log.info("Processing item: {}", item);
        OutputDataDto outputDataDto = new OutputDataDto();
        outputDataDto.setFirstName(item.getFirstName());
        outputDataDto.setLastName(item.getLastName());
        outputDataDto.setDisctrict(item.getDisctrict());
        outputDataDto.setMandal(item.getMandal());
        outputDataDto.setVillage(item.getVillage());
        return outputDataDto;
    }
}
