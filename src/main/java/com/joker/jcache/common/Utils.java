package com.joker.jcache.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Utils {
	private Utils() {}

	public static String[] getParameterNamesByAsm5(Class<?> clazz,  
			final Method method) {  
		System.out.println(clazz);
		System.out.println(method);
		final Class<?>[] parameterTypes = method.getParameterTypes();  
		if (parameterTypes == null || parameterTypes.length == 0) {  
			return null;  
		}  
		final Type[] types = new Type[parameterTypes.length];  
		for (int i = 0; i < parameterTypes.length; i++) {  
			types[i] = Type.getType(parameterTypes[i]);  
		}  
		final String[] parameterNames = new String[parameterTypes.length];  

		String className = clazz.getName();  
		int lastDotIndex = className.lastIndexOf(".");  
		className = className.substring(lastDotIndex + 1) + ".class";  
		InputStream is = clazz.getResourceAsStream(className);  
		try {  
			ClassReader classReader = new ClassReader(is);  
			classReader.accept(new ClassVisitor(Opcodes.ASM4) {  
				@Override  
				public MethodVisitor visitMethod(int access, String name,  
						String desc, String signature, String[] exceptions) {  
					// 只处理指定的方法  
					Type[] argumentTypes = Type.getArgumentTypes(desc);  
					if (!method.getName().equals(name)  
							|| !Arrays.equals(argumentTypes, types)) {  
						return super.visitMethod(access, name, desc, signature,  
								exceptions);  
					}  
					return new MethodVisitor(Opcodes.ASM4) {  
						@Override  
						public void visitLocalVariable(String name, String desc,  
								String signature, org.objectweb.asm.Label start,  
								org.objectweb.asm.Label end, int index) {  
							// 非静态成员方法的第一个参数是this  
							if (Modifier.isStatic(method.getModifiers())) {  
								parameterNames[index] = name;  
							} else if (index > 0) {  
								parameterNames[index - 1] = name;  
							}  
						}  
					};  
				}  
			}, 0);  
		} catch (IOException e) {  
		} finally {  
			try {  
				if (is != null) {  
					is.close();  
				}  
			} catch (Exception e2) {  
			}  
		}  
		return parameterNames;  
	}  
	
	
	public static Object handlerStringToObject(Object object,Class clazz) {
		Object result = null;
		 if (clazz == int.class) {
			result = Integer.parseInt((String) object);
		} else if (clazz == float.class) {
			result = Float.parseFloat((String)object);
		} else if (clazz == byte.class) {
			result = Byte.parseByte((String)object);
		} else if (clazz == double.class) {
			result = Double.parseDouble((String)object);
		} 
		return result;
	}
	
	public static boolean isJavaType(Class clazz) {
		if (clazz == int.class || clazz == byte.class || clazz == float.class || clazz == double.class || clazz == String.class) {
			return true;
		}
		return false;
	}
	
	
	public static String captureName(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return  name;

	}
	
	public static Class otherClassToBaseClass(Object object) {
		if (object instanceof Number) 
			return int.class;
		return null;
	}
}
