package org.cs.demo.manager.utils;

import java.lang.reflect.Method;


public class BAEHelper {
    public static String getBaeEnv(String key) {
        String ret = null;
        @SuppressWarnings("rawtypes")
        Class clazz;
        try {
            clazz = Class.forName("com.baidu.bae.api.util.BaeEnv");
            @SuppressWarnings("unchecked")
            Method method = clazz.getDeclaredMethod("getBaeHeader", String.class);
            ret = (String) method.invoke(clazz, key);
        } catch (Exception e) { }
        return ret;
    }
}
