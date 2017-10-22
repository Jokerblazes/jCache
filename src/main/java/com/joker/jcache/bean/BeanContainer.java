package com.joker.jcache.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import com.joker.jcache.common.Constant;
import com.joker.jcache.elimination.EliminationStrategy;

public class BeanContainer<T> extends Observable {
	private Map<Object,Bean<T>> beanMap;//
	private String resourceName;//资源名
//	private EliminationStrategy strategy;
	public BeanContainer() {
		beanMap = new ConcurrentHashMap<>();
	}
	public Bean<T> getBean(Object key) {
		return beanMap.get(key);
	}
	
	public void setBean(Bean<T> bean,Object key) {
		beanMap.put(key, bean);
		this.setChanged();
		notifyObservers(bean);
	}
	
	public void setBeans(List<Bean<T>> list,List<Object> keyList) {
		for (int i = 0 ; i < list.size() ; i++) {
			beanMap.put(keyList.get(i), list.get(i));
		}
		this.setChanged();
		notifyObservers(list);
	}
	
	public void removeBean(Object key) {
		//		if (beanMap.containsKey(key)) {
		Bean<T> bean = beanMap.remove(key);
		bean.setBean(null);	
		this.setChanged();
		notifyObservers(bean);
		//		}
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	} 
	
	public int getCurrentSize() {
		return beanMap.size();
	}
	
	
//	public Map<Object,Bean<T>> getFreeBean() {
////		List<Bean<T>> list = new ArrayList<>();
//		Map<Object,Bean<T>> map = new HashMap<>();
//		for (Object key : beanMap.keySet()) {
//			Bean<T> bean = beanMap.get(key);
//			if (bean.getFlag() == Constant.FREE_STATE)
//				map.put(key, bean);
//		}
//		return map;
//	}
	public Map<Object, Bean<T>> getBeanMap() {
		return beanMap;
	}
	
	
	
	
	
	
}
