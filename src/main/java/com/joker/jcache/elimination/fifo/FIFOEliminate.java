package com.joker.jcache.elimination.fifo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.common.Constant;
import com.joker.jcache.elimination.EliminationStrategy;

public class FIFOEliminate<T> extends EliminationStrategy<T> {
	private int size;
	private int multiple;
	public FIFOEliminate(int size,int multiple) {
		this.size = size;
		this.multiple = multiple;
		beans = new Vector<>();
	}
//	public boolean isNeedEliminate(BeanContainer container) {
//		if (container.getCurrentSize() >= size)
//			return true;
//		return false;
//	}
//
//	public void eliminate(BeanContainer container) {
//		Map<Object,Bean<T>> beanMap = container.getBeanMap();
//		for (int i = 0 ; i < size*multiple ; i++) {
//		}
//	}
	@Override
	public synchronized boolean isNeedEliminate() {
		if (beans.size() >= size)
			return true;
		return false;
	}

	@Override
	public synchronized void eliminate() {
		List<Bean<T>> removeList = new ArrayList<>();
		for (int i = 0 ; i < size*multiple ; i++)
			removeList.add(beans.get(i));
		beans.removeAll(removeList);
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
