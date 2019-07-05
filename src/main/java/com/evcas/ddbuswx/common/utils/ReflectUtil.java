package com.evcas.ddbuswx.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by noxn on 2018/9/19.
 */
public class ReflectUtil {

    public final static String SET_PREFIX = "set";

    public final static String GET_PREFIX = "get";

    public static <T> Method obtainGetMethod(Field field, Class<T> tClass) {
        String methodName = GET_PREFIX + StringUtil.changeFirstCharToUpperCase(field.getName());
        try {
            return tClass.getDeclaredMethod(methodName, (Class<?>) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Method obtainSetMethod(Field field, Class<T> tClass) {
        String methodName = SET_PREFIX + StringUtil.changeFirstCharToUpperCase(field.getName());
        try {
            return tClass.getMethod(methodName, field.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void setFieldValue(T t, Method method, Object value) {
        try {
            try {
                try {
                    method.invoke(t, value);
                } catch (InvocationTargetException exp) {
                    exp.printStackTrace();
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
