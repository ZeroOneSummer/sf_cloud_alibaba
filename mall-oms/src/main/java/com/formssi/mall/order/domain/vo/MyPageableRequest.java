package com.formssi.mall.order.domain.vo;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

/**
 * 自定分页类
 */
public class MyPageableRequest extends PageRequest {

    public MyPageableRequest(int page, int size, Sort sort) {
        super(page, size,sort);
    }

}
