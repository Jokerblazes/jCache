package com.joker.jcache.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.buffer.factory.BufferLinkFactory;
import com.joker.jcache.annoation.Cache.OperateType;
import com.joker.jcache.rule.DeleteRule;
import com.joker.jcache.rule.InsertRule;
import com.joker.jcache.rule.SelectRule;
import com.joker.jcache.rule.StrategyRule;
import com.joker.jcache.rule.UpdateRule;
import com.joker.jcache.strategy.CustomerStrategyParam;
import com.joker.jcache.strategy.DefaultStrategyParam;
import com.joker.jcache.strategy.StrategyParam;

/**
 * 策略工厂类（提供参数处理策略，数据库操作策略）
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 */
public class StrategyFactory {
	private static final Logger logger = LoggerFactory.getLogger(StrategyFactory.class);
	private StrategyFactory() {};
	private static Map<Boolean,StrategyParam> strategyMap = new ConcurrentHashMap<>();
	private static Map<OperateType,StrategyRule> ruleMap = new ConcurrentHashMap<>();
	static {
		logger.info("初始化StrategyFactory！");
		strategyMap.put(false, new DefaultStrategyParam());
		strategyMap.put(true, new CustomerStrategyParam());
		ruleMap.put(OperateType.INSERT, new InsertRule<>());
		ruleMap.put(OperateType.UPDATE, new UpdateRule<>());
		ruleMap.put(OperateType.DELETE, new DeleteRule<>());
		ruleMap.put(OperateType.SELECT, new SelectRule<>());
		logger.info("初始化StrategyFactory完成！");
	}
	
	public static void initAsynchronousDeleteRule(int size,int limit,Class strategyClass) {
		BufferLinkFactory.initBufferList(size, limit, strategyClass);
		DeleteRule rule = (DeleteRule) ruleMap.get(OperateType.DELETE);
		rule.setAsynchronous(true);
	}
	
	public static void initAsynchronousUpdateRule(int size,int limit,Class strategyClass) {
		BufferLinkFactory.initBufferList(size, limit, strategyClass);
		UpdateRule rule = (UpdateRule) ruleMap.get(OperateType.UPDATE);
		rule.setAsynchronous(true);
	}
	
	/**
	 * 
	 * @param length(@Cache注解中value数组的长度)
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	public static StrategyParam getStrategyParam(int length) {
		StrategyParam strategyParam = strategyMap.get(length!=0);
		logger.info("参数处理策略 {}",strategyParam);
		return strategyParam;
	}
	
	/**
	 * 获取操作策略
	 * @param type(@Cache注解中OperateType的类型)
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	public static StrategyRule getStrategyRule(OperateType type) {
		StrategyRule strategyRule = ruleMap.get(type);
		logger.info("操作处理策略 {}",strategyRule);
		return strategyRule;
	}
}
