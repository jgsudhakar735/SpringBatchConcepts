package com.jgsudhakar.springboot.batch.stepresume.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.tasklet.Tasklet3
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
public class Tasklet3 implements Tasklet {

    @Value("${filepath}")
    private String FILE_PATH = "D:\\readfile\\1.txt";

    @Value("${sleeptime}")
    private Integer SLEEP_TIME = 10000;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // SLEEP 10s for simulation a heavy processing
        Thread.sleep(SLEEP_TIME);

        System.out.println("### Step 3 - Processing!");
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw(e);
        }

        return null;
    }
}
