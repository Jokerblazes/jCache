package com.joker.jcache.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.buffer.factory.BufferLinkFactory;
import com.joker.jcache.bean.Bean;
import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.common.Invocation;
import com.joker.jcache.common.ReflectionUtils;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;

/**
 * 更新规则
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 * @param <T>
 */
public class UpdateRule<T> implements StrategyRule {
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateRule.class); 
	private boolean asynchronous = false;
	
	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}
	
	@Override
	public Object saveOrGetFromCache(Invocation invocation, CacheSingleton cacheSingleton, Condition condition) {
		Object dao = invocation.getController();
		Method method = invocation.getMethod();
		BeanContainer<T> beanContainer = cacheSingleton.getBeanContainer(invocation.getController().getClass());
		Object[] args = invocation.getArgs();
		Object result = null;
		if (asynchronous == false) {
			//执行数据库操作
			try {
				result = method.invoke(dao, invocation.getArgs());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			BufferLinkFactory.readObject(invocation);
			result = 1;
		}
		int number = ReflectionUtils.getCacheObjectNumberFromMethod(method);
		if (number == -1) 
			throw new RuntimeException("未设置@CacheBean");
		T object = (T) args[number];
		Object key = ReflectionUtils.getKeyByObject(object);
		Bean<T> bean = beanContainer.getBean(key);
		if (bean != null) {
			logger.info("缓存命中,更新前 {}",bean);
			bean.setBean(object);
			logger.info("缓存命中,更新后 {}",bean);
		}
		return result;
	}
}
