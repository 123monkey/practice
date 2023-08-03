package com.practice.review.newfactory;

import sun.misc.ClassLoaderUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 类加载工具类
 */
public class ClassLoaderUtils {

    private static Set<Class> primitiveSet = new HashSet<>();

    static {
        primitiveSet.add(Integer.class);
        primitiveSet.add(Long.class);
        primitiveSet.add(String.class);
        primitiveSet.add(Float.class);
        primitiveSet.add(Byte.class);
        primitiveSet.add(Short.class);
        primitiveSet.add(Double.class);
        primitiveSet.add(Character.class);
        primitiveSet.add(Boolean.class);
    }

    public static ClassLoader getCurrentClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoaderUtil.class.getClassLoader();
        }
        return cl == null ? cl : cl;
    }

    public static  ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader!=null) {
            return loader;
        }
        if (clazz!=null) {
          loader = clazz.getClassLoader();
          if(loader!=null) {
              return loader;
          }
          return clazz.getClassLoader();
        }
        return ClassLoader.getSystemClassLoader();
    }

    public static  Class<?>[] getClazzByArgs(Object[] args) {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length ; i++) {
            //相关类型匹配
            if(args[i] instanceof ArrayList) {
                parameterTypes[i] = List.class;
                continue;
            }
            if (args[i] instanceof LinkedList) {
                parameterTypes[i] = List.class;
                continue;
            }
            if (args[i] instanceof HashMap) {
                parameterTypes[i] = Map.class;
                continue;
            }
            if (args[i] instanceof Long){
                parameterTypes[i] = long.class;
                continue;
            }
            if (args[i] instanceof Double){
                parameterTypes[i] = double.class;
                continue;
            }
            if (args[i] instanceof TimeUnit){
                parameterTypes[i] = TimeUnit.class;
                continue;
            }

            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;
    }
}
