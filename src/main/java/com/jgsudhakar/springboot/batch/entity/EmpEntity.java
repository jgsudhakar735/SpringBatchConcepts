package com.jgsudhakar.springboot.batch.entity;

import com.jgsudhakar.springboot.batch.bulk.dto.InputDataDto;
import com.jgsudhakar.springboot.batch.constants.BatchConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*************************************
 * This Class is used to 
 * Author  : Sudhakar Tangellapalli
 * File    : com.jgsudhakar.springboot.batch.entity.EmpEntity
 * Date    : 14-08-2024
 * Version : 1.0
 **************************************/
@Entity
@Table(name = "SB_EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SqlResultSetMapping(
        name = BatchConstants.EMP_LIST_SQL_RESULT_SET,
        classes = @ConstructorResult(
                targetClass = InputDataDto.class,
                columns = {
                        @ColumnResult(name = "ID",type = Long.class),
                        @ColumnResult(name = "FIRST_NAME"),
                        @ColumnResult(name = "LAST_NAME")
                }
        )
)
public class EmpEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;


}
