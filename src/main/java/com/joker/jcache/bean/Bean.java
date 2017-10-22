package com.joker.jcache.bean;

import com.joker.jcache.condition.Condition;

public class Bean<T> {
	private T bean;
	private Condition condition;//条件
	private volatile int flag;
	
	public T getBean() {
		return bean;
	}
	public void setBean(T bean) {
		this.bean = bean;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
