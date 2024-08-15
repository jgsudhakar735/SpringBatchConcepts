package com.jgsudhakar.springboot.batch.repository;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*************************************
 * This interface is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.repository.EmplRepository
 * Date    : 14-08-2024
 * Version : 1.0
 **************************************/
@Repository
public interface EmplRepository extends JpaRepository<EmpEntity,Long> {
}
