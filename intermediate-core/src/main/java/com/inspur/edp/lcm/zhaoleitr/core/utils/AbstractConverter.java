package com.inspur.edp.lcm.zhaoleitr.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 转换器抽象类
 * 1、如果简单的属性转换，则继承此类使用即可
 * 2、如果实现自己的，重写该方法即可
 */
public class AbstractConverter {
    /**
     * 单个对象转换为目标类
     *
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T toTarget(S source, Class<T> tClass) {
        if (Objects.isNull(source)) {
            return null;
        }
        try {
            T target = tClass.newInstance();
            if (Objects.nonNull(target)) {
                BeanUtils.copyProperties(source, target);
            }
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("对象属性转换发生异常", e);
        }
    }

    /**
     * List转换为目标类
     *
     * @param source
     * @param tClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S extends List, T> List<T> toTargetList(S source, Class<T> tClass) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(source.size());
        source.forEach(s -> {
            T target = toTarget(s, tClass);
            targetList.add(target);
        });
        return targetList;
    }

}
