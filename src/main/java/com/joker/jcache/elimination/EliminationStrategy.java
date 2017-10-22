package com.joker.jcache.elimination;


import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.joker.jcache.bean.Bean;


public abstract class EliminationStrategy<T> implements Observer {
	protected List<Bean<T>> beans;
	 
	protected abstract boolean isNeedEliminate();
	
	protected abstract void eliminate();
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof List) {
			beans.addAll((List<Bean<T>>)arg);
		} else {
			Bean<T> bean = (Bean<T>) arg;
			if (bean.getBean() == null) 
				beans.remove(bean);
			else 
				beans.add(bean);
		}
		if (isNeedEliminate())
			eliminate();		
	}
	
}
