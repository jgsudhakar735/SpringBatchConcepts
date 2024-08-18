package com.jgsudhakar.springboot.batch.stepresume.util;

import org.apache.commons.lang3.StringUtils;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.stepresume.util.StepResumeConstants
 * Date    : 17-08-2024
 * Version : 1.0
 **************************************/
public class StepResumeConstants {

    private StepResumeConstants() {

    }

    /**
     * The value of the Job is initially EMPTY. Once the Step one executed then this value will be set to
     * a Random value. When the step 2 executes based on this value the job will be failed.
     * This value will be set to EMPTY again on step 2 execution.
     * */
    public static String CALLED_STEP= StringUtils.EMPTY;

    /**
     * The value of the Job is initially EMPTY. Once the Step one executed then this value will be set to
     * a Random value. When the step 3 executes based on this value the job will be failed.
     * This value will be set to EMPTY again on step 3 execution.
     * */
    public static String CALLED_STEP_3= StringUtils.EMPTY;
}
