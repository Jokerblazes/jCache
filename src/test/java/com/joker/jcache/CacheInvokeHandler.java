package com.joker.jcache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.joker.jcache.annoation.Cache;
import com.joker.jcache.common.Invocation;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;
import com.joker.jcache.factory.StrategyFactory;
import com.joker.jcache.rule.StrategyRule;
import com.joker.jcache.strategy.StrategyParam;

public class CacheInvokeHandler implements InvocationHandler {
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Cache cache = method.getAnnotation(Cache.class);
		if (cache == null)
			return method.invoke(userDao, args);
		String[] values = cache.value();
		Invocation invocation = new Invocation(method, userDao, args);
		StrategyParam strategyParam = StrategyFactory.getStrategyParam(values.length);
		Condition condition = strategyParam.handleParamToCondition(args, invocation,values);
		CacheSingleton cacheSingleton = CacheSingleton.getInstance();
		StrategyRule rule = StrategyFactory.getStrategyRule(cache.operateType());
		Object result = rule.saveOrGetFromCache(invocation, cacheSingleton, condition);
		return result;
	}

}
