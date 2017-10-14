package com.joker.jcache.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.joker.jcache.annoation.CacheBean;
import com.joker.jcache.annoation.Key;
import com.joker.jcache.condition.Condition;

public class ReflectionUtils {
	private ReflectionUtils() {}
	
	public static void setObjectKey(Object key,Object object) {
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String value = getFieldNameByClass(clazz);
		Method method = null;
		try {
			method = clazz.getMethod("set"+Utils.captureName(value),Utils.otherClassToBaseClass(key));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			method.invoke(object, key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Object getKeyByObject(Object object) {
		Class clazz = object.getClass();
		String value = getFieldNameByClass(clazz);
		Method method = null;
		try {
			method = clazz.getMethod("get"+Utils.captureName(value), null);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return method.invoke(object, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getCacheObjectNumberFromMethod(Method method) {
		Parameter[] parameters = method.getParameters();
		int i = 0;
		for (Parameter parameter : parameters) {
			CacheBean cacheBean = parameter.getAnnotation(CacheBean.class);
			if (cacheBean != null) 
				return i;
			i++;
		}
		return -1;
	}
	
	public static String getFieldNameByClass(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String value = null;
		for (Field field : fields) {
			Key keyAno = field.getDeclaredAnnotation(Key.class);
			if (keyAno != null) {
				return  keyAno.value();
			}
		}
		return null;
	}
	
	public static Object getObjectValue(Object object,String name,Class clazz) {
		Method method = null;
		try {
			method = clazz.getMethod("get"+Utils.captureName(name),null);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return method.invoke(object, null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean compareComditionAndObject(Object object,Condition condition) {
		Object[] args = condition.getArgs();
		String[] argNames = condition.getArgNames();
		if (args == null) 
			return true;
		Class clazz = object.getClass();
		for (int i = 0 ; i < args.length ; i++) {
			Object value = getObjectValue(object, argNames[i], clazz);
			if (!value.equals(args[i])) 
				return false;
		}
		return true;
	}
	
//	public static Map<String,Object> getCacheParamObject(Method method) {
//		
//	}
	
	
}
