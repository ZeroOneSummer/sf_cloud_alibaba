package com.formssi.mall.exception.util;

import com.formssi.mall.exception.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class ValidatorUtils {

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    public static void isBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new BusinessException(message);
        }
    }

    public static void isCollectionEmpty(Collection<?> collection, String message) {
        if (collection != null && CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

}
