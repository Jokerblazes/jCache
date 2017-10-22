package com.joker.jcache.elimination.lru;

import java.util.List;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.common.Constant;

public class LRURunnable<T> implements Runnable {
	private List<Bean<T>> beans;
	
	public LRURunnable(List<Bean<T>> beans) {
		this.beans = beans;
	}
	
	@Override
	public void run() {
		int i = 0;
		while (true) {
			if (beans.size() >= i) {
				break;
			}
			beans.get(i).setFlag(Constant.FREE_STATE);
			i++;
		}
	}
}
