package com.joker.jcache.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.common.Invocation;
import com.joker.jcache.common.ReflectionUtils;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;
import com.joker.jcache.list.ListBean;
import com.joker.jcache.list.ListBeanContainer;

/**
 * 查找规则
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 * @param <T>
 */
public class SelectRule<T> implements StrategyRule {
	
	private static final Logger logger = LoggerFactory.getLogger(SelectRule.class); 
	
	@Override
	public Object saveOrGetFromCache(Invocation invocation,CacheSingleton cacheSingleton,Condition condition) {
		Object dao = invocation.getController();
		Method method = invocation.getMethod();
		BeanContainer<T> beanContainer = cacheSingleton.getBeanContainer(dao.getClass());
		ListBeanContainer<T> listBeanContainer = cacheSingleton.getListBeanContainer(dao.getClass());
		ListBean<T> listBean = listBeanContainer.getList(condition);
		Object result = null;
		if (listBean == null) { //执行数据库查找并进行缓存
			logger.info("缓存中不存在对应数据！");
			listBean = new ListBean<T>();
			listBeanContainer.putlistBeanMap(condition, listBean);
			logger.info("构建ListBean {}存入ListBeanContainer，条件 {}",listBean,condition);
			listBean.setCondition(condition);
			beanContainer.addObserver(listBean);
			logger.info("将ListBean注册为beanContainer观察者 {}",beanContainer);
			try {
				result = method.invoke(dao, invocation.getArgs());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			List<T> actualList = (List<T>) result;
			logger.info("获取的实际List {}",actualList);
			List<Bean<T>> list = new ArrayList<>();
			List<Object> keyList = new ArrayList<>();
			for (T t : actualList) {
				Object key = ReflectionUtils.getKeyByObject(t);
				keyList.add(key);
				Bean<T> bean = new Bean<T>();
				bean.setBean(t);
				bean.setCondition(condition);
				list.add(bean);
			}
			logger.info("组装成BeanList {}",list);
			beanContainer.setResourceName(result.getClass().getName());
			beanContainer.setBean(list,keyList);
			logger.info("存储到bean容器中!");
		} else {
			logger.info("缓存命中!");
			List<T> actualList = new ArrayList<>();
			List<Bean<T>> list = listBean.getBeans();
			logger.info("缓存中的beanList {}",list);
			for (Bean<T> bean : list) {
				actualList.add(bean.getBean());
			}
			result = actualList;
			logger.info("实际list {}",actualList);
		}
		return result;
	}
	
}
