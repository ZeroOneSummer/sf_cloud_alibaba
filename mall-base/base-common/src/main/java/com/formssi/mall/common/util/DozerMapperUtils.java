package com.formssi.mall.common.util;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author jp
 * @date 2022年05月12日 13:46
 */
public final class DozerMapperUtils {
    private final static Mapper INSTANCE = DozerBeanMapperBuilder.buildDefault();

    public static <T, S> T map(S source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        Assert.notNull(clazz, "clazz can not be null");
        return INSTANCE.map(source, clazz);
    }

    public static <D, S> void map(S source, D des) {
        if (source == null) {
            return;
        }
        Assert.notNull(des, "des can not be null");
        INSTANCE.map(source, des);
    }

    /**
     * 避免与 map(S source, D des, BiConsumer<S, D> consumer) 方法产生歧义，可以改变一下clazz形参顺序。否则consumer要强转，格式如下：
     * DozerMapperUtils.map(user1, User2.class, (BiConsumer<User1, User2>) (s, t) -> {
     *             t.setAmount(BigDecimal.ZERO);
     *         });
     */
    public static <D, S> D map(S source, Class<D> clazz, BiConsumer<S, D> consumer) {
        if (source == null) {
            return null;
        }
        D des = BeanUtils.instantiateClass(clazz);
        INSTANCE.map(source, des);
        if (consumer != null){
            consumer.accept(source, des);
        }
        return des;
    }

    public static <D, S> void map(S source, D des, BiConsumer<S, D> consumer) {
        if (source == null) {
            return;
        }
        Assert.notNull(des, "des can not be null");
        INSTANCE.map(source, des);
        if (consumer != null){
            consumer.accept(source, des);
        }
    }

    public static <T, S> List<T> map(List<S> source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        Assert.notNull(clazz, "clazz can not be null");
        List<T> list = new ArrayList<>();
        for (S src : source) {
            T des = INSTANCE.map(src, clazz);
            list.add(des);
        }
        return list;
    }
}