package com.jgsudhakar.springboot.batch.service;

import com.jgsudhakar.springboot.batch.bulk.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.service.EmpService
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
@Component
public class EmpService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param startIndex
     * @param endIndex
     * @return
     */
    public List<InputDataDto> fetchEmpList(int pageSize, int initialCount) {
        Query query = entityManager.createNativeQuery(
                "SELECT ID, FIRST_NAME, LAST_NAME FROM SB_EMPLOYEE",
                BatchConstants.EMP_LIST_SQL_RESULT_SET
        );
        query.setFirstResult(initialCount);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
