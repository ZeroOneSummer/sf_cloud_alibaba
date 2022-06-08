package com.formssi.mall.mongodb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_book")
public class Book {
    @Id
    //属性名为 id 时不用加注解来映射, 查询时主键名称传 pkId 或 _id
    private String pkId;
    private String name;
    private Double price;
    private Date createDate;
}