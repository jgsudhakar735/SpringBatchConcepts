package com.jgsudhakar.springboot.batch.bulk.reader;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemReader;

import java.util.List;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.bulk.reader.AbstractPaginationReader
 * Date    : 17-04-2025
 * Version : 1.0
 **************************************/
public abstract class AbstractPaginationReader<T> implements ItemReader<T> {
    private List<T> recordList = null;
    private int currentIndex = 0;

    private int startRecordIndex = 0;
    @Getter
    @Setter
    private Integer pageSize = 0;
    @Getter
    private int totalRecords = 0;
    protected int initialCount;

    protected abstract List<T> readPage();

    @Override
    public T read() throws Exception {
        if(recordList == null || ( pageSize >0 && currentIndex >= pageSize)) {
            recordList = readPage();
            currentIndex=0;
        }
        if(recordList != null && currentIndex < recordList.size()){
            totalRecords++;
            return recordList.get(currentIndex++);
        }
        recordList = null;
        pageSize=0;
        initialCount=0;
        totalRecords=0;
        return null;
    }
}
