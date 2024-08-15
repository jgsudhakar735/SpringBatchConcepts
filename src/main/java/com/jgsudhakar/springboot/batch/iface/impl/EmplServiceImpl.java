package com.jgsudhakar.springboot.batch.iface.impl;

import com.jgsudhakar.springboot.batch.entity.EmpEntity;
import com.jgsudhakar.springboot.batch.iface.EmpIFace;
import com.jgsudhakar.springboot.batch.repository.EmplRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.iface.impl.EmplServiceImpl
 * Date    : 14-08-2024
 * Version : 1.0
 **************************************/
@Service
public class EmplServiceImpl implements EmpIFace {

    @Autowired
    private EmplRepository emplRepository;

    @Override
    public EmpEntity fetchById(Long id) {
        Optional<EmpEntity> byId = emplRepository.findById(id);
        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public List<EmpEntity> fetchAll() {
        return emplRepository.findAll();
    }
}
