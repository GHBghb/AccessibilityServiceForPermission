package com.example.xingxiaogang.testfingerprint.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Method;

/**
 * Created by Potter on 2017/4/28.
 * Where there is a will there is a way.
 */
public class ReflectUtils {

    /**
     * 获取某个类声明的方法,包括public,protected,private方法,不包括从基类或接口继承或实现的方法
     *
     * @param desiredClass
     * @param methodName
     * @param methodParams
     * @return
     */
    public static
    @Nullable
    Method getClassMethod(@NonNull Class<?> desiredClass, @NonNull String methodName, Class<?>[] methodParams) {
        try {
            if (methodParams == null) {
                return desiredClass.getDeclaredMethod(methodName, new Class[0]);
            } else {
                return desiredClass.getDeclaredMethod(methodName, methodParams);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static
    @Nullable
    Object invokeClassMethod(Object receiver, @NonNull Method method, Object... params) {
        Object result;
        try {
            boolean isAccessible = method.isAccessible();
            if (!isAccessible) {
                method.setAccessible(true);
            }
            result = method.invoke(receiver, params);
            if (!isAccessible) {
                method.setAccessible(false);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
