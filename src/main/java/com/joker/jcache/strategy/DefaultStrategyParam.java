package com.joker.jcache.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jcache.common.Invocation;
import com.joker.jcache.condition.Condition;

/**
 * 增删改参数处理方式
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 */
public class DefaultStrategyParam implements StrategyParam {
	private static final Logger logger = LoggerFactory.getLogger(DefaultStrategyParam.class);
	@Override
	public Condition handleParamToCondition(Object[] args,Invocation invocation,String[] values) {
		Condition condition = new Condition();
		condition.setArgs(args);
		condition.setMethodName(invocation.getMethod().getName());
		logger.info("参数处理后 {}",condition);
		return condition;
	}
	
	
}
