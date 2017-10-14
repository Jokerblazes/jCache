package com.joker.jcache.common;

import java.lang.reflect.Method;

/**
 * 反射容器类
 * @author joker
 */
public class Invocation {
	
	private Method method;//反射方法类
	private Object controller;//控制实例
	private Object[] args;//参数
	
	
	public Invocation(Method method, Object controller, Object[] args) {
		super();
		this.method = method;
		this.controller = controller;
		this.args = args;
	}


	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	
}
