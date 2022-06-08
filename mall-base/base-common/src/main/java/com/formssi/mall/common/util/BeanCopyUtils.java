package com.formssi.mall.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean复制工具类
 *
 * @author zhangmiao
 */
public class BeanCopyUtils {

    public static <S, T> T copyProperties(S source, Class<T> type) {
        T target = BeanUtils.instantiateClass(type);
        return copyProperties(source, target, null);
    }

    public static <S, T> T copyProperties(S source, T target) {
        return copyProperties(source, target, null);
    }

    public static <S, T> T copyProperties(S source, T target, Assembler<S, T> assembler) {
        if (source != null) {
            BeanUtils.copyProperties(source, target);
            if (assembler != null) {
                assembler.assemble(source, target);
            }
        }
        return target;
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Class<T> type) {
        return copyListProperties(sources, type, null);
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Class<T> type, Assembler<S, T> assembler) {
        List<T> targetList = new ArrayList<>();
        if (sources != null) {
            for (S source : sources) {
                T target = BeanUtils.instantiateClass(type);
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
                if (assembler != null) {
                    assembler.assemble(source, target);
                }
            }
        }
        return targetList;
    }

    @FunctionalInterface
    public interface Assembler<S, T> {

        /**
         * 装配 请实现装配过程
         * @param source 源
         * @param target 目标
         */
        void assemble(S source, T target);

    }

}