package com.joker.jcache.strategy;

import com.joker.jcache.common.Invocation;
import com.joker.jcache.condition.Condition;

/**
 * 参数处理接口
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 */
public interface StrategyParam {

	/**
	 * 处理参数
	 * @param args
	 * @param invocation
	 * @param values
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	Condition handleParamToCondition(Object[] args,Invocation invocation,String[] values);
	
}
