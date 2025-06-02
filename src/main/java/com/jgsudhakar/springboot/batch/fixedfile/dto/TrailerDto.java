package com.jgsudhakar.springboot.batch.fixedfile.dto;

import lombok.Data;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.dto.TrailerDto
 * Date    : 01-06-2025
 * Version : 1.0
 **************************************/
@Data
public class TrailerDto extends BaseDto{
    private String trailerData;

    @Override
    public String toString() {
        return "TrailerDto{" +
                "trailerData='" + trailerData + '\'' +
                '}';
    }
}
