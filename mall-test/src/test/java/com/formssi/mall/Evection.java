package com.formssi.mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Evection implements Serializable {

    private Long id;                //主键ID
    private Integer days;            //出差天数
    private String evectionName;    //出差单名字
    private Date startTime;         //出差开始时间
    private Date endTime;           //出差结束时间
    private String address;         //目的地
    private String reason;          //出差原因

}
