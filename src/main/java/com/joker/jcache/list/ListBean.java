package com.joker.jcache.list;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.condition.Condition;

public class ListBean<T> implements Observer {
	private List<Bean<T>> beans;//bean的list
	private Condition condition;//筛选的条件
	
	public ListBean() {
		beans = new Vector<>();
	}
	public synchronized List<Bean<T>> getBeans() {
		return beans;
	}
//	public Condition getCondition() {
//		return condition;
//	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
//	public void addBeans(Bean<T> bean) {
//		beans.add(bean);
//	}
	
//	public void removeBean(Bean<T> bean) {
//		beans.remove(bean);
//	}
	@Override
	public synchronized void update(Observable o, Object arg) {
		if (arg instanceof List) {
			List<Bean<T>> argBeans = (List<Bean<T>>) arg;
			Condition argCondition = argBeans.get(0).getCondition();
			if (!argCondition.equals(condition))
				return;
			for (Bean<T> bean : argBeans)
				beans.add(bean);
		} else {
			Bean<T> bean = (Bean<T>) arg;
			if (!bean.getCondition().equals(condition))
				return;
			if (bean.getBean() == null)
				beans.remove(bean);
			else {
				beans.remove(bean);
				beans.add(bean);
			}
		}
	}
	
	
	
	
}
