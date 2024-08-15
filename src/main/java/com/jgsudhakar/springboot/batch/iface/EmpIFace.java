package com.jgsudhakar.springboot.batch.iface;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;

import java.util.List;

/*************************************
 * This interface is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.iface.EmpIFace
 * Date    : 14-08-2024
 * Version : 1.0
 **************************************/
public interface EmpIFace {

    public EmpEntity fetchById(Long id);

    public List<EmpEntity> fetchAll();

}
