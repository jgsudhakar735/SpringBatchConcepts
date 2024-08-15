package com.jgsudhakar.springboot.batch.tasklet.utils;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.tasklet.utils.FileUtils
 * Date    : 15-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class FileUtils {
    private String fileName;
    private CSVReader CSVReader;
    private CSVWriter CSVWriter;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private File file;

    public FileUtils(String fileName) {
        this.fileName = fileName;
    }

    public EmpEntity readLine() {
        try {
            if (CSVReader == null) initReader();
            String[] line = CSVReader.readNext();
            if (line == null) return null;
            return new EmpEntity(Long.parseLong(line[0]), line[1],line[2]);
        } catch (Exception e) {
            log.error("Error while reading line in file: " + this.fileName);
            return null;
        }
    }

    public void writeLine(EmpEntity line) {
        try {
            if (CSVWriter == null) initWriter();
            String[] lineStr = new String[3];
            lineStr[0] = line.getId()+"";
            lineStr[1] = line
                    .getFirstName()
                    .toString();
            lineStr[2] = line
                    .getLastName()
                    .toString();
            CSVWriter.writeNext(lineStr);
        } catch (Exception e) {
            log.error("Error while writing line in file: " + this.fileName);
        }
    }

    private void initReader() throws Exception {
        ClassLoader classLoader = this
                .getClass()
                .getClassLoader();
        if (file == null) file = new File(classLoader
                .getResource(fileName)
                .getFile());
        if (fileReader == null) fileReader = new FileReader(file);
        if (CSVReader == null) CSVReader = new CSVReader(fileReader);
    }

    private void initWriter() throws Exception {
        if (file == null) {
            file = new File(fileName);
            file.createNewFile();
        }
        if (fileWriter == null) fileWriter = new FileWriter(file, true);
        if (CSVWriter == null) CSVWriter = new CSVWriter(fileWriter);
    }

    public void closeWriter() {
        try {
            CSVWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            log.error("Error while closing writer.");
        }
    }

    public void closeReader() {
        try {
            CSVReader.close();
            fileReader.close();
        } catch (IOException e) {
            log.error("Error while closing reader.");
        }
    }

}
