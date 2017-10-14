package com.joker.jcache.rule;

import com.joker.jcache.common.Invocation;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;

/**
 * 规则类接口-策略模式
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 */
public interface StrategyRule {
	
	/**
	 * 更新、查找、删除、修改缓存
	 * @param invocation
	 * @param cacheSingleton
	 * @param condition
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	Object saveOrGetFromCache(Invocation invocation, CacheSingleton cacheSingleton, Condition condition);
	
}
