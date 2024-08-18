package com.jgsudhakar.springboot.batch.stepresume.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.tasklet.Tasklet1
 * Date    : 18-08-2024
 * Version : 1.0
 **************************************/
public class Tasklet1 implements Tasklet {
    /**
     * Given the current context in the form of a step contribution, do whatever is
     * necessary to process this unit inside a transaction. Implementations return
     * {@link RepeatStatus#FINISHED} if finished. If not they return
     * {@link RepeatStatus#CONTINUABLE}. On failure throws an exception.
     *
     * @param contribution mutable state to be passed back to update the current step
     *                     execution
     * @param chunkContext attributes shared between invocations but not between restarts
     * @return an {@link RepeatStatus} indicating whether processing is continuable.
     * Returning {@code null} is interpreted as {@link RepeatStatus#FINISHED}
     * @throws Exception thrown if error occurs during execution.
     */
    public String FOLDER_PATH = "D:\\readfile";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("### Step 1 - Processing!");
        try{
            // delete folder recursively
            recursiveEmptyFolder(new File(FOLDER_PATH));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void recursiveEmptyFolder(File file) {
        // stop condition
        if (!file.exists())
            return;

        // if non-empty folder, call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // recursive function
                recursiveEmptyFolder(f);
            }
        }
        // call delete() function for file and empty directory
        if(!file.getAbsolutePath().equals(FOLDER_PATH)){
            file.delete();
        }
        System.out.println("Deleted folder(file): " + file.getAbsolutePath());
    }
}
