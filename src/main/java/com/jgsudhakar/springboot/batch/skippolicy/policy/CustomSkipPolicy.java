package com.jgsudhakar.springboot.batch.skippolicy.policy;

import com.jgsudhakar.springboot.batch.exception.NameStartsWithException;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.skippolicy.policy.CustomSkipPolicy
 * Date    : 16-08-2024
 * Version : 1.0
 **************************************/
@Log4j2
public class CustomSkipPolicy implements SkipPolicy {

    private static final int MAX_SKIP_COUNT = 2;

    /**
     * Here we can add any validation
     * @param throwable
     * @param skipCount
     * @return
     * @throws SkipLimitExceededException
     */
    @Override
    public boolean shouldSkip(Throwable throwable, long skipCount) throws SkipLimitExceededException {
        log.info("There is an Skip Item . Current Skip Count : {} and Max Skip Count is : {}", skipCount , MAX_SKIP_COUNT);
        if (throwable instanceof NameStartsWithException && skipCount < MAX_SKIP_COUNT) {
            return true;
        }
        return false;
    }
}
