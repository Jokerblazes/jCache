package com.joker.jcache.elimination.lru;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.common.Constant;
import com.joker.jcache.elimination.EliminationStrategy;
//implements EliminationStrategy
public class LRUEliminate<T> extends EliminationStrategy<T> {
	private int size;
	private LRURunnable<T> runnable;
	public LRUEliminate(int size) {
		this.size = size;
		beans =  new Vector<>();
		runnable = new LRURunnable<T>(beans);
		//初始化的时候就执行定时线程
	}
//	public void eliminate(BeanContainer container) {
//		Map<Object,Bean<T>> beanMap = container.getBeanMap();
//		Map<Object,Bean<T>> map = new HashMap<>();
//		for (Object key : beanMap.keySet()) {
//			Bean<T> bean = beanMap.get(key);
//			if (bean.getFlag() == Constant.FREE_STATE)
//				map.put(key, bean);
//		}
//		for (Object key : map.keySet()) {
//			container.removeBean(key);
//			beans.remove(map.get(key));
//		}
//	}
	@Override
	public synchronized void eliminate() {
		List<Bean<T>> removeList = new ArrayList<>();
		for (Bean<T> bean : beans) {
			if (bean.getFlag() == Constant.FREE_STATE)
				removeList.add(bean);
		}
		beans.removeAll(beans);
	}
	
//	public boolean isNeedEliminate(BeanContainer container) {
//		final int currentSize = container.getCurrentSize();
//		if (currentSize < this.size)
//			return false;
//		return true;
//	}
	@Override
	public synchronized boolean isNeedEliminate() {
		if (beans.size() >= size)
			return true;
		return false;
	}
	
	
//	@Override
//	public void addBean(Bean bean) {
//		beans.add(bean);
//	}
//	
//	@Override
//	public void addBeans(List beans) {
//		beans.addAll(beans);
//	}
}
