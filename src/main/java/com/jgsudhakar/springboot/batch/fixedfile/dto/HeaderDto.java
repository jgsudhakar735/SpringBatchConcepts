package com.jgsudhakar.springboot.batch.fixedfile.dto;

import lombok.Data;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.dto.HeaderDto
 * Date    : 01-06-2025
 * Version : 1.0
 **************************************/
@Data
public class HeaderDto extends BaseDto {
    private String headerData;

    // toString method to print the header data
    @Override
    public String toString() {
        return "HeaderDto{" +
                "headerData='" + headerData + '\'' +
                '}';
    }
}
