package com.joker.jcache.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.joker.jcache.condition.Condition;

public class ListBeanContainer<T>  {
	private Map<Condition,ListBean<T>> listBeanMap;
	
	public ListBeanContainer() {
		listBeanMap = new ConcurrentHashMap<>();
	}
	
	public ListBean<T> getList(Condition condition) {
		return listBeanMap.get(condition);
	}
	
	public void putlistBeanMap(Condition condition,ListBean<T> list) {
		listBeanMap.put(condition, list);
	}
	
	public List<Condition> getAllCondition() {
		List<Condition> list = new ArrayList<>();
		for (Condition condition : listBeanMap.keySet()) {
			list.add(condition);
		}
		return list;
	}
	
//	@Override
//	public void update(Observable o, Object arg) {
//		if (arg instanceof List) {
//			List<Bean<T>> list = (List<Bean<T>>) arg;
//			ListBean<T> listBean = new ListBean<>();
//			listBean.setBeans(list);
//			listBean.setCondition(list.get(0).getCondition());
//			listBeanMap.put(listBean.getCondition(), listBean);
//		} else {
//			boolean flag = true;
//			Bean<T> bean = (Bean<T>) arg;
//			if (bean.getBean() == null)
//				flag = false;
//			ListBean<T> listBean = listBeanMap.get(bean.getCondition());
//			if (listBean == null && flag) {//map中没有listBean并且是非移除操作
//				listBean = new ListBean<>();
//				listBean.setCondition(bean.getCondition());
//				listBean.addBeans(bean);
//				listBeanMap.put(bean.getCondition(), listBean);
//			} else if (listBean != null && !flag) {//map中存在listBean并且是移除操作
//				listBean.removeBean(bean);
//			} else {//map中有listBean并且是非移除操作
//				listBean.addBeans(bean);
//			}
//		}
//	}
	
	
}
