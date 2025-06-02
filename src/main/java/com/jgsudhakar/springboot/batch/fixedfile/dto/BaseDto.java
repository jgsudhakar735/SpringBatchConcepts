package com.jgsudhakar.springboot.batch.fixedfile.dto;

import lombok.Data;

import java.io.Serializable;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.fixedfile.dto.BaseDto
 * Date    : 01-06-2025
 * Version : 1.0
 **************************************/
@Data
public class BaseDto implements Serializable {
    private String recordType;
}
